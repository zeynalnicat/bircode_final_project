package com.example.register.data

import com.example.core.AppErrors
import com.example.core.Result
import com.example.register.domain.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SignUpRepositoryImpl @Inject constructor(val firebaseAuth: FirebaseAuth,val firestore: FirebaseFirestore): SignUpRepository {
    override suspend fun signUp(
        email: String,
        name: String,
        password: String
    ): Result<Unit> = suspendCoroutine { continuation ->
        val collection = firestore.collection("users")
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val user = hashMapOf(
                        "userId" to authResult.user?.uid,
                        "name" to name,
                        "pin" to "",
                        "profilePhoto" to ""
                    )

                    collection.add(user)
                        .addOnSuccessListener {
                            continuation.resume(Result.Success(Unit))
                        }
                        .addOnFailureListener {
                            continuation.resume(Result.Error(it.message ?: AppErrors.unknownError))
                        }

                }.addOnFailureListener {
                    continuation.resume(Result.Error(it.message ?: AppErrors.authFailed))
                }
        }catch (e:Exception){
            continuation.resume(Result.Error(e.message ?: AppErrors.unknownError))
        }

    }
}