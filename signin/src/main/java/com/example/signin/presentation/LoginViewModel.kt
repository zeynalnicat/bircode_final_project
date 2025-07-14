package com.example.signin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.core.AppStrings
import com.example.core.CoreViewModel
import com.example.core.Result
import com.example.core.ScreenModel
import com.example.signin.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel(),
    CoreViewModel<LoginIntent> {

    private val _state = MutableStateFlow(LoginState())

    private val _effect = MutableSharedFlow<LoginUiEffect>()

    private var navController: NavController? = null

    override fun initiateController(navController: NavController) {
        this.navController = navController
    }

    val state = _state.asStateFlow()
    val effect = _effect.asSharedFlow()

    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.OnChangeEmail -> _state.update { it.copy(email = intent.email) }
            is LoginIntent.OnChangePassword -> _state.update { it.copy(password = intent.password) }
            LoginIntent.OnNavigateToRegister -> navController?.popBackStack()
            LoginIntent.OnSubmit -> onSubmit()
        }
    }

    private fun onSubmit() {

        _state.update { it.copy(loading = true, emailError = "", passwordError = "", enabled = false) }
        val email = _state.value.email
        val password = _state.value.password

        if (email.isEmpty()) {
            _state.update { it.copy(emailError = AppStrings.emptyField, loading = false, enabled = true) }
        }

        if (password.isEmpty()) {
            _state.update { it.copy(passwordError = AppStrings.emptyField, loading = false, enabled = true) }
        }

        if (_state.value.emailError.isEmpty() && _state.value.passwordError.isEmpty()) {
            viewModelScope.launch {
                when (val res = loginUseCase.invoke(email, password)) {
                    is Result.Error -> {
                        _state.update { it.copy(loading = false, enabled = true) }
                        _effect.emit(LoginUiEffect.OnShowError(res.message))
                    }

                    is Result.Success<*> -> navController?.navigate(ScreenModel.Home.route) {
                        popUpTo(
                            ScreenModel.Login.route
                        ) { inclusive = true }
                    }
                }
            }

        }


    }

}