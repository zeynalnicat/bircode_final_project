package com.example.transactionreceipt.data

import com.example.common.domain.TransactionModel
import com.example.core.AppErrors
import com.example.core.Result
import com.example.transactionreceipt.domain.TransactionDetailRepository
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TransactionDetailRepositoryImpl @Inject constructor(private val firebaseFirestore: FirebaseFirestore) :
    TransactionDetailRepository {

    private val transactionCollection = firebaseFirestore.collection("transactions")
    private val userCollection = firebaseFirestore.collection("users")
    override suspend fun getTransactionDetail(id: String): Result<TransactionModel> =
        suspendCoroutine { continuation ->
            try {

                transactionCollection.whereEqualTo("transactionId", id).get()
                    .addOnSuccessListener { snapshots ->

                        if (snapshots.documents.isNotEmpty()) {
                            val t1 = snapshots.documents[0]
                            val transactionModel = TransactionModel(
                                id = id,
                                cardId = t1.get("cardId") as String,
                                userId = t1.get("userId") as String,
                                date = t1.get("date") as String,
                                isExpense = t1.get("isExpense") as Boolean,
                                amount = t1.get("amount") as String,
                                transactionName = t1.get("transactionName") as String
                            )
                            userCollection.whereEqualTo("userId", transactionModel.userId).get()
                                .addOnSuccessListener { s1 ->
                                    if (s1.documents.isNotEmpty()) {
                                        val transactionRes =
                                            transactionModel.copy(name = s1.documents[0].get("name") as String)
                                        continuation.resume(Result.Success(transactionRes))
                                    } else {
                                        continuation.resume(Result.Success(transactionModel))
                                    }
                                }.addOnFailureListener { ex0 ->
                                    continuation.resume(
                                        Result.Error(
                                            ex0.message ?: AppErrors.unknownError
                                        )
                                    )
                                }
                        } else {
                            continuation.resume(Result.Error(AppErrors.unknownError))
                        }

                    }.addOnFailureListener { ex0 ->
                        continuation.resume(Result.Error(ex0.message ?: AppErrors.unknownError))
                    }


            } catch (e: Exception) {
                continuation.resume(Result.Error(e.message ?: AppErrors.unknownError))
            }
        }
}