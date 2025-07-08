package com.example.bircodefinal.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebase(): Firebase = Firebase

    @Singleton
    @Provides
    fun provideFirestore(firebase: Firebase): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun provideFireAuth(firebase: Firebase): FirebaseAuth = Firebase.auth
}