package com.example.pin.presentation

sealed class PinIntent {

    data class OnPressDigit(val digit:String): PinIntent()

    data object OnClear: PinIntent()

    data object OnRemoveDigit: PinIntent()

    data object OnGetName: PinIntent()
}