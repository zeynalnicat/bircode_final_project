package com.example.pin.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PinViewModel: ViewModel(){

    private val _state = MutableStateFlow(PinState())
    val state = _state.asStateFlow()

    fun onIntent(intent: PinIntent){
        when(intent) {
            is PinIntent.OnPressDigit -> onHandleNumberPress(intent.digit)
            PinIntent.OnSubmit -> TODO()
        }
    }

    private fun onHandleNumberPress(digit:String){
        if (_state.value.currentIndex < 6) {
            val newPin = _state.value.pin.toMutableList()
            newPin[_state.value.currentIndex] = digit
            _state.update { it.copy(pin = newPin, currentIndex = _state.value.currentIndex+1) }
        }
    }

}