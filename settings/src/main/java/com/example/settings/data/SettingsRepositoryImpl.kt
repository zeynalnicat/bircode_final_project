package com.example.settings.data

import com.example.core.AppErrors
import com.example.core.Result
import com.example.settings.domain.SettingsRepository
import com.google.firebase.auth.FirebaseAuth
import jakarta.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SettingsRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    SettingsRepository {
    override suspend fun onLogOut(): Result<Unit> = suspendCoroutine { continuation ->
        try {
            firebaseAuth.signOut()
            continuation.resume(Result.Success(Unit))

        } catch (e: Exception) {
            continuation.resume(Result.Error(e.message ?: AppErrors.errorWhileLogout))
        }
    }
}