package com.example.payoperation.data

import com.example.common.domain.CardModel
import com.example.core.Result
import com.example.payoperation.domain.PayOperationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PayOperationRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth,private val firebaseFirestore: FirebaseFirestore) : PayOperationRepository {

    val collection = firebaseFirestore.collection("cards")

    override suspend fun getCards(): Result<List<CardModel>> = suspendCoroutine {continuation ->

        try {
            firebaseAuth.currentUser?.let {
                collection.whereEqualTo("userId",it.uid).get().addOnSuccessListener { snapshots ->

                    if(snapshots.documents.isNotEmpty()){
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
                    }else{
                        continuation.resume(Result.Success(emptyList()))
                    }

                }.addOnFailureListener { ex0->
                    continuation.resume(Result.Error(ex0.message ?: "Unexpected Error"))
                }
            }
        }catch (e:Exception){
             continuation.resume(Result.Error(e.message ?: "Unexpected Error"))
        }
    }
}