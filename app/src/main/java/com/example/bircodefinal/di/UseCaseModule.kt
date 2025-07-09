package com.example.bircodefinal.di

import com.example.pin.domain.EnterPinUseCase
import com.example.pin.domain.PinGetNameUseCase
import com.example.pin.domain.PinRepository
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
    fun provideSignUpUseCase(signUpRepository: SignUpRepository): SignUpUseCase = SignUpUseCase(signUpRepository)

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase = LoginUseCase(loginRepository)

    @Provides
    fun providePinGetNameUseCase(pinRepository: PinRepository): PinGetNameUseCase =
        PinGetNameUseCase(pinRepository)

    @Provides
    fun provideEnterPinUseCase(pinRepository: PinRepository): EnterPinUseCase = EnterPinUseCase(pinRepository)

}