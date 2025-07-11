package com.example.carddetails.presentation

sealed class CardDetailUiEffect {

    data class OnShowError(val message:String): CardDetailUiEffect()
}