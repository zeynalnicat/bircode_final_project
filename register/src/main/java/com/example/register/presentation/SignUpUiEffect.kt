package com.example.register.presentation

sealed class SignUpUiEffect {

    data class OnShowError(val error:String): SignUpUiEffect()
}