package com.example.signin.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.core.AppStrings
import com.example.core.ScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update



class LoginViewModel(private val navController: NavController): ViewModel(){

    private val _state = MutableStateFlow(LoginState())

    private var navController: NavController? = null

    fun initiateController(navController: NavController){
        this.navController = navController
    }

    val state = _state.asStateFlow()

    fun onIntent(intent: LoginIntent){
        when(intent){
            is LoginIntent.OnChangeEmail -> _state.update { it.copy(email = intent.email) }
            is LoginIntent.OnChangePassword -> _state.update { it.copy(password = intent.password) }
            LoginIntent.OnNavigateToRegister -> navController.popBackStack()
            LoginIntent.OnSubmit -> onSubmit()
        }
    }

    private fun onSubmit() {

        _state.update { it.copy(loading = true, emailError = "", passwordError = "") }
        val email = _state.value.email
        val password = _state.value.password

        val emailError = _state.value.emailError
        val passwordError = _state.value.passwordError

        if (email.isEmpty()) {
            _state.update { it.copy(emailError = AppStrings.emptyField) }
        }

        if(password.isEmpty()){
            _state.update { it.copy(passwordError = AppStrings.emptyField) }
        }

        if(emailError.isEmpty() && passwordError.isEmpty()){
            _state.update { it.copy(loading = false, emailError = "", passwordError = "") }
            navController.navigate(ScreenModel.Home.route){popUpTo(ScreenModel.Login.route){inclusive=true} }
        }




    }

}