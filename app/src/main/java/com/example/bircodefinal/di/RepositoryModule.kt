package com.example.bircodefinal.di

import com.example.carddetails.data.CardDetailsRepositoryImpl
import com.example.carddetails.domain.CardDetailsRepository
import com.example.home.data.HomeRepositoryImpl
import com.example.home.domain.HomeRepository
import com.example.newcard.data.NewCardRepositoryImpl
import com.example.newcard.domain.NewCardRepository
import com.example.payoperation.data.PayOperationRepositoryImpl
import com.example.payoperation.domain.PayOperationRepository
import com.example.pin.data.PinRepositoryImpl
import com.example.pin.domain.PinRepository
import com.example.profile.data.ProfileRepositoryImpl
import com.example.profile.domain.ProfileRepository
import com.example.register.data.SignUpRepositoryImpl
import com.example.register.domain.SignUpRepository
import com.example.signin.data.LoginRepositoryImpl
import com.example.signin.domain.LoginRepository
import com.example.transactionreceipt.data.TransactionDetailRepositoryImpl
import com.example.transactionreceipt.domain.TransactionDetailRepository
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
    fun provideSignRepo(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): SignUpRepository =
        SignUpRepositoryImpl(firebaseAuth, firestore)

    @Provides
    fun provideLoginRepo(firebaseAuth: FirebaseAuth): LoginRepository =
        LoginRepositoryImpl(firebaseAuth)

    @Provides
    fun providePinRepo(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): PinRepository =
        PinRepositoryImpl(firebaseAuth, firebaseFirestore)

    @Provides
    fun provideNewCardRepo(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): NewCardRepository =
        NewCardRepositoryImpl(firebaseAuth, firebaseFirestore)

    @Provides
    fun provideHomeRepo(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): HomeRepository =
        HomeRepositoryImpl(firebaseAuth, firebaseFirestore)

    @Provides
    fun provideProfileRepo(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): ProfileRepository =
        ProfileRepositoryImpl(firebaseAuth, firebaseFirestore)

    @Provides
    fun providePayOperationRepo(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): PayOperationRepository =
        PayOperationRepositoryImpl(firebaseAuth, firebaseFirestore)


    @Provides
    fun provideCardDetailsRepo(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): CardDetailsRepository =
        CardDetailsRepositoryImpl(firebaseAuth, firebaseFirestore)

    @Provides
    fun provideTransactionDetailRepo(
        firebaseFirestore: FirebaseFirestore
    ): TransactionDetailRepository = TransactionDetailRepositoryImpl(firebaseFirestore)
}


