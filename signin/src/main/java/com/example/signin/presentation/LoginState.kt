package com.example.signin.presentation

data class LoginState(
    val email:String = "",
    val password:String = "",
    val loading: Boolean = false,
    val emailError: String = "",
    val passwordError:String = "",

)