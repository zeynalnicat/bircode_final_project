package com.example.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.core.CoreViewModel
import com.example.core.Result
import com.example.core.ScreenModel
import com.example.settings.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(private val logoutUseCase: LogoutUseCase) : ViewModel(),
    CoreViewModel<SettingsIntent> {

    private val _effect = MutableSharedFlow<SettingsUiEffect>()

    val effect = _effect.asSharedFlow()

    private var navController: NavController? = null


    override fun initiateController(navController: NavController) {
        this.navController = navController
    }

    override fun onIntent(intent: SettingsIntent) {
        when (intent) {
            SettingsIntent.OnLogOut -> onLogOut()
            SettingsIntent.OnNavigateToProfile -> navController?.navigate(ScreenModel.Profile.route)
        }
    }

    private fun onLogOut() {
        viewModelScope.launch {
            when(val res = logoutUseCase()){
                is Result.Error -> _effect.emit(SettingsUiEffect.OnShowError(res.message))
                is Result.Success<*> -> navController?.navigate(ScreenModel.SignUp.route){
                    popUpTo(ScreenModel.Settings.route) { inclusive = true }
                }
            }
        }
    }
}