package com.example.home.data

import com.example.core.Result
import com.example.home.domain.CardModel
import com.example.home.domain.HomeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HomeRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth,private val firebaseFirestore: FirebaseFirestore): HomeRepository {

    private val collection = firebaseFirestore.collection("cards")

    override suspend fun getUserCards(): Result<List<CardModel>> = suspendCoroutine{ continuation ->
        try {
            firebaseAuth.currentUser?.let {
                collection.whereEqualTo("userId",it.uid).get().addOnSuccessListener { snapshots ->
                    if(snapshots.documents.isNotEmpty()){
                        val cards = snapshots.documents.map { document->
                            CardModel(document.get("cardId") as String,document.get("cardHolder") as String, document.get("color") as String, document.get("deposit") as String, document.get("cardNumber") as String)
                        }
                        continuation.resume(Result.Success(cards))
                    }else{
                        continuation.resume(Result.Success(emptyList()))
                    }

                }.addOnFailureListener { ex0 ->
                    continuation.resume(Result.Error(ex0.message ?: "Unexpected Error"))
                }
            }
        }catch (e:Exception){
               continuation.resume(Result.Error(message = e.message ?: "Unexpected Error"))
        }
    }
}