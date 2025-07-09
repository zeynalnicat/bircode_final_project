package com.example.newcard.presentation

import com.example.common.presentation.theme.Gray

data class NewCardState (
    val color: String = Gray.value.toString(),
    val name:String = "",
    val initialBalance: String = "0",
    val loading: Boolean = false
)