package com.example.pin.presentation

sealed class PinIntent {

    data class OnPressDigit(val digit:String): PinIntent()
    data object OnSubmit: PinIntent()
}