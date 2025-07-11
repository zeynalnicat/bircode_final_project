package com.example.bircodefinal.di

import com.example.carddetails.domain.CardDetailsGetCardUseCase
import com.example.carddetails.domain.CardDetailsRepository
import com.example.home.domain.GetUserCardsUseCase
import com.example.home.domain.HomeGetCardTransactionsUseCase
import com.example.home.domain.HomeRepository
import com.example.newcard.domain.CreateNewCardUseCase
import com.example.newcard.domain.NewCardRepository
import com.example.payoperation.domain.PayOperationGetCardsUseCase
import com.example.payoperation.domain.PayOperationRepository
import com.example.payoperation.domain.PayOperationSaveTransactionUseCase
import com.example.pin.domain.EnterPinUseCase
import com.example.pin.domain.PinGetNameUseCase
import com.example.pin.domain.PinRepository
import com.example.profile.domain.GetProfileDetailsUseCase
import com.example.profile.domain.ProfileRepository
import com.example.register.domain.SignUpRepository
import com.example.register.domain.SignUpUseCase
import com.example.signin.domain.LoginRepository
import com.example.signin.domain.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideSignUpUseCase(signUpRepository: SignUpRepository): SignUpUseCase =
        SignUpUseCase(signUpRepository)

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase =
        LoginUseCase(loginRepository)

    @Provides
    fun providePinGetNameUseCase(pinRepository: PinRepository): PinGetNameUseCase =
        PinGetNameUseCase(pinRepository)

    @Provides
    fun provideEnterPinUseCase(pinRepository: PinRepository): EnterPinUseCase =
        EnterPinUseCase(pinRepository)

    @Provides
    fun provideCreateNewCardUseCase(newCardRepository: NewCardRepository): CreateNewCardUseCase =
        CreateNewCardUseCase(newCardRepository)

    @Provides
    fun provideGetUserCardsUseCase(homeRepository: HomeRepository): GetUserCardsUseCase =
        GetUserCardsUseCase(homeRepository)

    @Provides
    fun provideGetProfileDetailsUseCase(profileRepository: ProfileRepository): GetProfileDetailsUseCase =
        GetProfileDetailsUseCase(profileRepository)

    @Provides
    fun providePayOperationGetCardsUseCase(payOperationRepository: PayOperationRepository): PayOperationGetCardsUseCase =
        PayOperationGetCardsUseCase(payOperationRepository)

    @Provides
    fun providePayOperationSaveTransactionUseCase(payOperationRepository: PayOperationRepository): PayOperationSaveTransactionUseCase =
        PayOperationSaveTransactionUseCase(payOperationRepository)

    @Provides
    fun provideHomeGetCardTransactionsUseCase(homeRepository: HomeRepository): HomeGetCardTransactionsUseCase =
        HomeGetCardTransactionsUseCase(homeRepository)

    @Provides
    fun provideCardDetailsGetCardUseCase(cardDetailsRepository: CardDetailsRepository): CardDetailsGetCardUseCase =
        CardDetailsGetCardUseCase(cardDetailsRepository)
}