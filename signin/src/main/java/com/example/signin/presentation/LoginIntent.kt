package com.example.signin.presentation

sealed class LoginIntent {

    data object OnNavigateToRegister: LoginIntent()
    data class OnChangeEmail(val email:String): LoginIntent()
    data class OnChangePassword(val password:String): LoginIntent()
    data object OnSubmit: LoginIntent()
}