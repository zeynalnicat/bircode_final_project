package com.example.payoperation.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.common.domain.CardModel
import com.example.core.AppErrors
import com.example.core.Result
import com.example.payoperation.domain.PayOperationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PayOperationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : PayOperationRepository {

    val collection = firebaseFirestore.collection("cards")
    val transactionCollection = firebaseFirestore.collection("transactions")

    override suspend fun getCards(): Result<List<CardModel>> = suspendCoroutine { continuation ->

        try {
            firebaseAuth.currentUser?.let {
                collection.whereEqualTo("userId", it.uid).get().addOnSuccessListener { snapshots ->

                    if (snapshots.documents.isNotEmpty()) {
                        val cards = snapshots.documents.map { c0 ->
                            CardModel(
                                cardId = c0.get("cardId") as String,
                                cardHolder = c0.get("cardHolder") as String,
                                cardNumber = c0.get("cardNumber") as String,
                                cardColor = c0.get("color") as String,
                                availableBalance = c0.get("deposit") as String
                            )
                        }

                        continuation.resume(Result.Success(cards))
                    } else {
                        continuation.resume(Result.Success(emptyList()))
                    }

                }.addOnFailureListener { ex0 ->
                    continuation.resume(Result.Error(ex0.message ?: AppErrors.unknownError))
                }
            }
        } catch (e: Exception) {
            continuation.resume(Result.Error(e.message ?: AppErrors.unknownError))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun saveTransaction(
        cardId: String,
        amount: String,
        transactionName: String
    ): Result<String> = suspendCoroutine { continuation ->

        try {
            val transactionId = UUID.randomUUID().toString()
            firebaseAuth.currentUser?.let { auth ->
                transactionCollection.add(
                    hashMapOf(
                        "transactionId" to transactionId,
                        "cardId" to cardId,
                        "userId" to auth.uid,
                        "amount" to amount,
                        "transactionName" to transactionName,
                        "date" to LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        "isExpense" to true

                    )
                ).addOnSuccessListener {
                    collection.whereEqualTo("userId", auth.uid).whereEqualTo("cardId", cardId).get()
                        .addOnSuccessListener { cSnapshot ->
                            val reference = cSnapshot.documents[0].reference.path
                            val newAmount =
                                (cSnapshot.documents[0].get("deposit") as String).toInt() - amount.toInt()
                            firebaseFirestore.document(reference).update(
                                "deposit", newAmount.toString()
                            ).addOnSuccessListener {
                                continuation.resume(Result.Success(transactionId))
                            }.addOnFailureListener { ex0 ->
                                continuation.resume(
                                    Result.Error(
                                        ex0.message ?: AppErrors.unknownError
                                    )
                                )
                            }

                        }

                }.addOnFailureListener { ex0 ->
                    continuation.resume(Result.Error(ex0.message ?: AppErrors.unknownError))
                }
            }

        } catch (e: Exception) {
            continuation.resume(Result.Error(e.message ?: AppErrors.unknownError))
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun topUpBalance(
        cardId: String,
        amount: String,
        transactionName: String
    ): Result<String> = suspendCoroutine { continuation ->
        try {
            val transactionId = UUID.randomUUID().toString()
            firebaseAuth.currentUser?.let { auth ->
                transactionCollection.add(
                    hashMapOf(
                        "transactionId" to transactionId,
                        "cardId" to cardId,
                        "userId" to auth.uid,
                        "amount" to amount,
                        "transactionName" to transactionName,
                        "date" to LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        "isExpense" to false

                    )
                ).addOnSuccessListener {
                    collection.whereEqualTo("userId", auth.uid).whereEqualTo("cardId", cardId).get()
                        .addOnSuccessListener { cSnapshot ->
                            val reference = cSnapshot.documents[0].reference.path
                            val newAmount =
                                (cSnapshot.documents[0].get("deposit") as String).toInt() + amount.toInt()
                            firebaseFirestore.document(reference).update(
                                "deposit", newAmount.toString()
                            ).addOnSuccessListener {
                                continuation.resume(Result.Success(transactionId))
                            }.addOnFailureListener { ex0 ->
                                continuation.resume(
                                    Result.Error(
                                        ex0.message ?: AppErrors.unknownError
                                    )
                                )
                            }

                        }

                }.addOnFailureListener { ex0 ->
                    continuation.resume(Result.Error(ex0.message ?: AppErrors.unknownError))
                }
            }

        } catch (e: Exception) {
            continuation.resume(Result.Error(e.message ?: AppErrors.unknownError))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun transferTo(
        cardId: String,
        amount: String,
        transactionName: String,
        receiverCardNumber: String
    ): Result<String> = suspendCoroutine { continuation ->
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            continuation.resume(Result.Error("User not authenticated"))
            return@suspendCoroutine
        }

        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val receiverCardSnapshot = collection
                    .whereEqualTo("cardNumber", receiverCardNumber)
                    .get()
                    .await()

                if (receiverCardSnapshot.isEmpty) {
                    continuation.resume(Result.Error(AppErrors.noCardFound))
                    return@launch
                }

                val receiverDoc = receiverCardSnapshot.documents[0]
                val receiverCardId = receiverDoc.getString("cardId") ?: ""
                val receiverUserId = receiverDoc.getString("userId") ?: ""

                val transactionId = UUID.randomUUID().toString()
                transactionCollection.add(
                    mapOf(
                        "userId" to currentUser.uid,
                        "cardId" to cardId,
                        "amount" to amount,
                        "date" to date,
                        "isExpense" to true,
                        "transactionName" to transactionName,
                        "transactionId" to transactionId
                    )
                ).await()

                val senderSnapshot = collection.whereEqualTo("cardId", cardId).get().await()
                if (senderSnapshot.isEmpty) {
                    continuation.resume(Result.Error("Sender card not found"))
                    return@launch
                }

                val senderDoc = senderSnapshot.documents[0]
                val senderRef = senderDoc.reference
                val senderDeposit = (senderDoc.getString("deposit") ?: "0").toLong()
                val senderNewDeposit = senderDeposit - amount.toLong()

                if (senderNewDeposit < 0) {
                    continuation.resume(Result.Error("Insufficient funds"))
                    return@launch
                }

                senderRef.update("deposit", senderNewDeposit.toString()).await()

                transactionCollection.add(
                    mapOf(
                        "userId" to receiverUserId,
                        "cardId" to receiverCardId,
                        "amount" to amount,
                        "date" to date,
                        "isExpense" to false,
                        "transactionName" to transactionName,
                        "transactionId" to UUID.randomUUID().toString()
                    )
                ).await()

                val receiverSnapshot =
                    collection.whereEqualTo("cardId", receiverCardId).get().await()
                if (receiverSnapshot.isEmpty) {
                    continuation.resume(Result.Error("Receiver card not found after transaction"))
                    return@launch
                }

                val receiverDoc2 = receiverSnapshot.documents[0]
                val receiverRef = receiverDoc2.reference
                val receiverDeposit = (receiverDoc2.getString("deposit") ?: "0").toLong()
                val receiverNewDeposit = receiverDeposit + amount.toLong()
                receiverRef.update("deposit", receiverNewDeposit.toString()).await()

                continuation.resume(Result.Success(transactionId))
            } catch (e: Exception) {
                continuation.resume(Result.Error(e.message ?: AppErrors.errorWhileTransaction))
            }
        }
    }

}
