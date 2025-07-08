package com.example.register.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.core.AppStrings
import com.example.core.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SignUpViewModel(private val navController: NavController): ViewModel() {

    private val _state = MutableStateFlow(SignUpState())

    val state = _state.asStateFlow()


    fun onIntent(intent: SignUpIntent){
        when(intent){
            is SignUpIntent.OnChangeEmail -> _state.update { it.copy(email = intent.email) }
            is SignUpIntent.OnChangeName -> _state.update { it.copy(name = intent.name) }
            is SignUpIntent.OnChangePassword -> _state.update { it.copy(password = intent.password) }
            SignUpIntent.OnSubmit -> onSubmit()
            SignUpIntent.OnNavigateToLogin -> navController.navigate(ScreenModel.Login.route)
        }
    }

    private fun onSubmit(){
        _state.update { it.copy(loading = true, emailError = "", passwordError = "", nameError = "") }

        val email = _state.value.email
        val password = _state.value.password
        val name = _state.value.name

        val emailError = _state.value.emailError
        val passwordError = _state.value.passwordError
        val nameError = _state.value.nameError

        if(name.isEmpty()){
            _state.update { it.copy(nameError = AppStrings.emptyField) }
        }

        if(email.isEmpty()){
            _state.update { it.copy(emailError = AppStrings.emptyField) }
        }

        if(password.isEmpty()){
            _state.update { it.copy(passwordError = AppStrings.emptyField) }
        }

        if(!_state.value.email.contains("@")){
            _state.update { it.copy(emailError = AppStrings.notValidEmail, loading = false) }
        }

        if(emailError.isEmpty() && passwordError.isEmpty() && nameError.isEmpty()){
            _state.update { it.copy(emailError = "", passwordError = "", nameError = "") }
            navController.navigate(ScreenModel.Home.route,){popUpTo(ScreenModel.SignUp.route) { inclusive = true }}
        }

    }
}