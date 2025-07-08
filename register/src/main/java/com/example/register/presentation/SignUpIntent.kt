package com.example.register.presentation

sealed class SignUpIntent {

    data class OnChangeEmail(val email:String): SignUpIntent()
    data class OnChangeName(val name:String): SignUpIntent()
    data class OnChangePassword(val password:String): SignUpIntent()

    data object OnSubmit: SignUpIntent()

    data object OnNavigateToLogin : SignUpIntent()
}