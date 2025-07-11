package com.example.newcard.data

import com.example.core.AppErrors
import com.example.core.Result
import com.example.newcard.domain.NewCardRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewCardRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : NewCardRepository {

    private val collection = firebaseFirestore.collection("cards")

    override suspend fun createNewCard(
        cardHolder: String,
        cardColor: String,
        deposit: String,
        cardNumber:String
    ): Result<Unit> = suspendCoroutine { continuation ->
        try {
            firebaseAuth.currentUser?.let {
                collection.add(
                    hashMapOf(
                        "cardId" to UUID.randomUUID().toString(),
                        "userId" to it.uid,
                        "cardHolder" to cardHolder,
                        "color" to cardColor,
                        "deposit" to deposit,
                        "cardNumber" to cardNumber
                    )
                ).addOnSuccessListener {
                    continuation.resume(Result.Success(Unit))
                }.addOnFailureListener { ex0 ->
                    continuation.resume(Result.Error(message = ex0.message ?: AppErrors.unknownError))
                }
            }
        } catch (e: Exception) {
              continuation.resume(Result.Error(e.message ?: AppErrors.unknownError))
        }
    }
}