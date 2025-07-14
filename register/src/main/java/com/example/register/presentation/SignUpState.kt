package com.example.register.presentation

data class SignUpState(
    val email:String = "",
    val name:String = "",
    val password:String = "",
    val loading: Boolean = false,
    val emailError: String = "",
    val nameError: String = "",
    val passwordError:String = "",
    val enabled: Boolean = true,
)