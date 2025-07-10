package com.example.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.CoreViewModel
import com.example.core.Result
import com.example.profile.domain.GetProfileDetailsUseCase
import com.example.profile.domain.ProfileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val getProfileDetailsUseCase: GetProfileDetailsUseCase): ViewModel(), CoreViewModel<ProfileIntent> {

    private val _state = MutableStateFlow(ProfileState())

    private val _effect = MutableSharedFlow<ProfileUiEffect>()

    val state = _state.asStateFlow()

    val effect = _effect.asSharedFlow()

    private var navController: NavController? = null


    override fun initiateController(navController: NavController) {
        this.navController = navController
    }

    override fun onIntent(intent: ProfileIntent) {
          when(intent){
              ProfileIntent.OnNavigateBack -> navController?.popBackStack()
              ProfileIntent.OnGetProfileDetails -> getProfileDetails()
          }
    }

    private fun getProfileDetails(){
        viewModelScope.launch {
            when(val res = getProfileDetailsUseCase()){
                is Result.Error -> _effect.emit(ProfileUiEffect.OnShowError(res.message))
                is Result.Success<ProfileModel> -> _state.update { it.copy(name = res.data.name, email = res.data.email, profileImg = res.data.profilePhoto) }
            }
        }
    }



}