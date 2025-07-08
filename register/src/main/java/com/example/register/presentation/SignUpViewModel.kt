package com.example.register.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SignUpViewModel: ViewModel() {

    private val _state = MutableStateFlow(SignUpState())

    val state = _state.asStateFlow()


    fun onIntent(intent: SignUpIntent){
        when(intent){
            is SignUpIntent.OnChangeEmail -> _state.update { it.copy(email = intent.email) }
            is SignUpIntent.OnChangeName -> _state.update { it.copy(name = intent.name) }
            is SignUpIntent.OnChangePassword -> _state.update { it.copy(password = intent.password) }
        }
    }
}