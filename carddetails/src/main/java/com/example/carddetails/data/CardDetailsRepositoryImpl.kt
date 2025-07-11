package com.example.carddetails.data

import com.example.carddetails.domain.CardDetailsRepository
import com.example.common.domain.CardModel
import com.example.core.AppErrors
import com.example.core.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CardDetailsRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : CardDetailsRepository {

    private val cardCollection = firebaseFirestore.collection("cards")
    private val transactionCollection = firebaseFirestore.collection("transactions")

    override suspend fun getCardDetails(cardId: String): Result<CardModel> =
        suspendCoroutine { continuation ->
            try {

                firebaseAuth.currentUser?.let { auth ->
                    cardCollection.whereEqualTo("userId", auth.uid).whereEqualTo("cardId", cardId)
                        .get().addOnSuccessListener { snapshots ->
                            if (snapshots.documents.isNotEmpty()) {
                                val document = snapshots.documents[0]
                                val cardModel = CardModel(
                                    cardId = document.get("cardId") as String,
                                    cardHolder = document.get("cardHolder") as String,
                                    cardColor = document.get("color") as String,
                                    availableBalance = document.get("deposit") as String,
                                    cardNumber = document.get("cardNumber") as String
                                )
                                continuation.resume(Result.Success(cardModel))
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