package com.example.profile.presentation

sealed class ProfileUiEffect {

    data class OnShowError(val message:String): ProfileUiEffect()
}