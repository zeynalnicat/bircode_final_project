package com.example.bircodefinal.di

import com.example.register.domain.SignUpRepository
import com.example.register.domain.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    fun provideSignUpUseCase(signUpRepository: SignUpRepository): SignUpUseCase = SignUpUseCase(signUpRepository)

}