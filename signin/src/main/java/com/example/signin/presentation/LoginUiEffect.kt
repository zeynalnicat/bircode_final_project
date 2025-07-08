package com.example.signin.presentation


sealed class LoginUiEffect  {

    data class OnShowError(val message: String): LoginUiEffect()
}