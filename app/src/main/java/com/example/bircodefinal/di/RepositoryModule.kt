package com.example.bircodefinal.di

import com.example.newcard.data.NewCardRepositoryImpl
import com.example.newcard.domain.NewCardRepository
import com.example.pin.data.PinRepositoryImpl
import com.example.pin.domain.PinRepository
import com.example.register.data.SignUpRepositoryImpl
import com.example.register.domain.SignUpRepository
import com.example.signin.data.LoginRepositoryImpl
import com.example.signin.domain.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideSignRepo(firebaseAuth: FirebaseAuth,firestore: FirebaseFirestore): SignUpRepository =
        SignUpRepositoryImpl(firebaseAuth,firestore)

    @Provides
    fun provideLoginRepo(firebaseAuth: FirebaseAuth): LoginRepository = LoginRepositoryImpl(firebaseAuth)

    @Provides
    fun providePinRepo(firebaseAuth: FirebaseAuth,firebaseFirestore: FirebaseFirestore): PinRepository =
        PinRepositoryImpl(firebaseAuth,firebaseFirestore)

    @Provides
    fun provideNewCardRepo(firebaseAuth: FirebaseAuth,firebaseFirestore: FirebaseFirestore): NewCardRepository =
        NewCardRepositoryImpl(firebaseAuth,firebaseFirestore)
 }


