package com.example.profile.data

import com.example.core.Result
import com.example.profile.domain.ProfileModel
import com.example.profile.domain.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : ProfileRepository {

    val collection = firebaseFirestore.collection("users")
    override suspend fun getProfileDetails(): Result<ProfileModel> =
        suspendCoroutine { continuation ->

            try {
                firebaseAuth.currentUser?.let {
                    collection.whereEqualTo("userId", it.uid).get()
                        .addOnSuccessListener { snapshots ->
                            if (snapshots.documents.isNotEmpty()) {
                                val res = snapshots.documents[0]
                                val user = ProfileModel(
                                    res.get("name") as String,
                                    it.email as String,
                                    res.get("profilePhoto") as String
                                )
                                continuation.resume(Result.Success(user))
                            } else {
                                continuation.resume(Result.Success(ProfileModel("", "", "")))
                            }

                        }
                }

            } catch (e: Exception) {
                   continuation.resume(Result.Error(e.message ?: "Unexpected Error"))
            }
        }
}