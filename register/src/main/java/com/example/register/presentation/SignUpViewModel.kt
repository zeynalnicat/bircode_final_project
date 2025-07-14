package com.example.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.core.AppStrings
import com.example.core.CoreViewModel
import com.example.core.Result
import com.example.core.ScreenModel
import com.example.register.domain.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel(),
    CoreViewModel<SignUpIntent> {

    private var navController: NavController? = null

    override fun initiateController(navController: NavController) {
        this.navController = navController
    }

    private val _state = MutableStateFlow(SignUpState())

    private val _effect = MutableSharedFlow<SignUpUiEffect>()

    val effect = _effect.asSharedFlow()

    val state = _state.asStateFlow()


    override fun onIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.OnChangeEmail -> _state.update { it.copy(email = intent.email) }
            is SignUpIntent.OnChangeName -> _state.update { it.copy(name = intent.name) }
            is SignUpIntent.OnChangePassword -> _state.update { it.copy(password = intent.password) }
            SignUpIntent.OnSubmit -> onSubmit()
            SignUpIntent.OnNavigateToLogin -> navController?.navigate(ScreenModel.Login.route)
        }
    }

    private fun onSubmit() {
        _state.update {
            it.copy(
                loading = true,
                emailError = "",
                passwordError = "",
                nameError = ""
            )
        }

        val email = _state.value.email
        val password = _state.value.password
        val name = _state.value.name




        if (name.isEmpty()) {
            _state.update { it.copy(nameError = AppStrings.emptyField, loading = false) }
        }

        if (email.isEmpty()) {
            _state.update { it.copy(emailError = AppStrings.emptyField, loading = false) }
        }

        if (password.length < 6) {
            _state.update {
                it.copy(
                    passwordError = AppStrings.passwordLenghtError,
                    loading = false
                )
            }
        }

        if (password.isEmpty()) {
            _state.update { it.copy(passwordError = AppStrings.emptyField, loading = false) }
        }

        if (!email.contains("@")) {
            _state.update { it.copy(emailError = AppStrings.notValidEmail, loading = false) }
        }

        val emailError = _state.value.emailError
        val passwordError = _state.value.passwordError
        val nameError = _state.value.nameError

        if (emailError.isEmpty() && passwordError.isEmpty() && nameError.isEmpty()) {
            _state.update { it.copy(emailError = "", passwordError = "", nameError = "") }
            viewModelScope.launch {
                when (val res = signUpUseCase.invoke(email, name, password)) {
                    is Result.Error -> {
                        _effect.emit(SignUpUiEffect.OnShowError(res.message))
                        _state.update { it.copy(loading = false) }
                    }

                    is Result.Success<*> -> navController?.navigate(ScreenModel.Home.route) {
                        popUpTo(
                            ScreenModel.SignUp.route
                        ) { inclusive = true }
                    }
                }
            }

        }

    }
}