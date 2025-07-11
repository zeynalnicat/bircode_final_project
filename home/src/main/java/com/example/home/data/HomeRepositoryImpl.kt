package com.example.home.data

import com.example.core.Result
import com.example.common.domain.CardModel
import com.example.common.domain.TransactionModel
import com.example.core.AppErrors
import com.example.home.domain.HomeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HomeRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : HomeRepository {

    private val collection = firebaseFirestore.collection("cards")
    private val transactionCollection = firebaseFirestore.collection("transactions")

    override suspend fun getUserCards(): Result<List<CardModel>> =
        suspendCoroutine { continuation ->
            try {
                firebaseAuth.currentUser?.let {
                    collection.whereEqualTo("userId", it.uid).get()
                        .addOnSuccessListener { snapshots ->
                            if (snapshots.documents.isNotEmpty()) {
                                val cards = snapshots.documents.map { document ->
                                    CardModel(
                                        document.get("cardId") as String,
                                        document.get("cardHolder") as String,
                                        document.get("color") as String,
                                        document.get("deposit") as String,
                                        document.get("cardNumber") as String
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
                continuation.resume(Result.Error(message = e.message ?: AppErrors.unknownError))
            }
        }

    override suspend fun getCardTransactions(cardId: String): Result<List<TransactionModel>> =
        suspendCoroutine { continuation ->
            try {
                firebaseAuth.currentUser?.let { auth ->
                    transactionCollection.whereEqualTo("userId", auth.uid)
                        .whereEqualTo("cardId", cardId).get().addOnSuccessListener { snapshots ->
                            if (snapshots.documents.isNotEmpty()) {
                                val transactionModel = snapshots.documents.map { document ->
                                    TransactionModel(
                                        id = document.get("transactionId") as String,
                                        userId = auth.uid,
                                        cardId = cardId,
                                        amount = document.get("amount") as String,
                                        date = document.get("date") as String,
                                        isExpense = document.get("isExpense") as Boolean,
                                        transactionName = document.get("transactionName") as String
                                    )

                                }

                                continuation.resume(Result.Success(transactionModel))
                            } else {
                                continuation.resume(Result.Success(emptyList()))
                            }
                        }.addOnFailureListener { ex0 ->
                            continuation.resume(
                                Result.Error(
                                    ex0.message ?: AppErrors.unknownError
                                )
                            )

                        }
                }
            } catch (e: Exception) {
                continuation.resume(
                    Result.Error(
                        e.message ?: AppErrors.unknownError
                    )
                )
            }
        }
}