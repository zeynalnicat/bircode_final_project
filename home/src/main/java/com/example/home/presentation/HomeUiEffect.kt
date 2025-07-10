package com.example.home.presentation

sealed class HomeUiEffect {

    data class OnShowError(val message:String): HomeUiEffect()
}