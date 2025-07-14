package com.example.pin.presentation

sealed class PinIntent {

    data class OnPressDigit(val digit:String): PinIntent()

    data class OnSetIsChangePinScreen(val isChangePin: Boolean): PinIntent()

    data object OnNavigateBack : PinIntent()

    data object OnClear: PinIntent()

    data object OnRemoveDigit: PinIntent()

    data object OnGetName: PinIntent()
}