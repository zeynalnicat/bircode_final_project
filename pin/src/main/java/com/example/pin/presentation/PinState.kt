package com.example.pin.presentation

data class PinState(
    val pin: List<String> = List(6){""},
    val error:String = "",
    val currentIndex:Int = 0,
)