package com.example.pin.data

import com.example.core.AppErrors
import com.example.core.Result
import com.example.pin.domain.PinRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.exp

class PinRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : PinRepository {

    private val collection = firebaseFirestore.collection("users")
    override suspend fun getName(): Result<String> = suspendCoroutine { continuation ->
        try {
            firebaseAuth.currentUser?.let {
                collection.whereEqualTo("userId", it.uid).get().addOnSuccessListener { snapshots ->
                    if (snapshots.documents.isNotEmpty()) {
                        continuation.resume(
                            Result.Success(
                                snapshots.documents[0].get("name") as? String ?: ""
                            )
                        )
                    }
                }.addOnFailureListener {
                    continuation.resume(Result.Error(AppErrors.unknownError))
                }

            }
        } catch (e: Exception) {
            continuation.resume(Result.Error(e.message.toString()))
        }

    }

    override suspend fun enterPin(pin: String): Result<Unit> = suspendCoroutine { continuation ->
        try {
            firebaseAuth.currentUser?.let {
                collection.whereEqualTo("userId", it.uid).get().addOnSuccessListener { snapshots ->

                    if (snapshots.documents.isNotEmpty()) {
                        val collectionId = snapshots.documents[0].reference.path
                        val dPin = snapshots.documents[0].get("pin") as? String ?: ""
                        if (dPin.isNotEmpty()) {
                            if (pin == dPin) {
                                continuation.resume(Result.Success(Unit))
                            } else {
                                continuation.resume(Result.Error(AppErrors.wrongPin))
                            }
                        } else {
                            firebaseFirestore.document(collectionId).update(
                                "pin", pin
                            ).addOnSuccessListener {
                                continuation.resume(Result.Success(Unit))
                            }.addOnFailureListener { ex0 ->
                                continuation.resume(Result.Error(ex0.message.toString()))
                            }
                        }
                    }

                }.addOnFailureListener { ex0 ->
                    continuation.resume(Result.Error(ex0.message.toString()))
                }
            }


        } catch (e: Exception) {
            continuation.resume(Result.Error(message = e.message ?: AppErrors.unknownError))
        }
    }

    override suspend fun changePin(newPin: String): Result<Unit> =
        suspendCoroutine { continuation ->
            try {

                firebaseAuth.currentUser?.let { auth ->
                    collection.whereEqualTo("userId", auth.uid).get()
                        .addOnSuccessListener { snapshots ->
                            if (snapshots.documents.isNotEmpty()) {
                                val ref = snapshots.documents[0].reference.path

                                firebaseFirestore.document(ref).update(
                                    "pin", newPin
                                ).addOnSuccessListener {
                                    continuation.resume(Result.Success(Unit))
                                }.addOnFailureListener { ex0 ->
                                    continuation.resume(
                                        Result.Error(
                                            ex0.message ?: AppErrors.unknownError
                                        )
                                    )
                                }
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