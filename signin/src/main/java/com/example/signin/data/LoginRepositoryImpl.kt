package com.example.signin.data

import com.example.core.AppErrors
import com.example.core.Result
import com.example.signin.domain.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth): LoginRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> = suspendCoroutine{ continuation ->

        try {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                continuation.resume(Result.Success(Unit))
            }.addOnFailureListener {
                continuation.resume(Result.Error(it.message ?: AppErrors.unknownError))
            }
        }catch (e:Exception){
            continuation.resume(Result.Error(e.message?: AppErrors.unknownError))
        }

    }


}