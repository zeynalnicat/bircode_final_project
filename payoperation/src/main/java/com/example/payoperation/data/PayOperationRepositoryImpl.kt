package com.example.payoperation.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.common.domain.CardModel
import com.example.core.AppErrors
import com.example.core.Result
import com.example.payoperation.domain.PayOperationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
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
    ): Result<Unit> = suspendCoroutine { continuation ->

        try {
            firebaseAuth.currentUser?.let { auth ->
                transactionCollection.add(
                    hashMapOf(
                        "transactionId" to UUID.randomUUID().toString(),
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
                                continuation.resume(Result.Success(Unit))
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
}