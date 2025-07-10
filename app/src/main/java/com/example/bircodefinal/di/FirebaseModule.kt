package com.example.bircodefinal.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebase(): Firebase = Firebase

    @Provides
    fun provideFirestore(firebase: Firebase): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFireAuth(firebase: Firebase): FirebaseAuth = Firebase.auth

    @Provides
    fun provideFireStorage(firebase:Firebase): FirebaseStorage = Firebase.storage
}