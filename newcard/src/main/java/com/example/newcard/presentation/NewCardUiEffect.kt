package com.example.newcard.presentation

sealed class NewCardUiEffect {

    data class OnShowError(val message:String): NewCardUiEffect()
}