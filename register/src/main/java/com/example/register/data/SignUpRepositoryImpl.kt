package com.example.register.data

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
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val user = hashMapOf(
                    "userId" to authResult.user?.uid,
                    "name" to name,
                    "pin" to ""
                )

                collection.add(user)
                    .addOnSuccessListener {
                        continuation.resume(Result.Success(Unit))
                    }
                    .addOnFailureListener {
                        continuation.resume(Result.Error(it.message ?: "Firestore write failed"))
                    }

            }.addOnFailureListener {
                continuation.resume(Result.Error(it.message ?: "Authentication failed"))
            }
    }
}