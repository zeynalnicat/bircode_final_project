package com.example.newcard.presentation

import com.example.common.presentation.theme.Pink40

data class NewCardState (
    val color: String = Pink40.value.toString(),
    val name:String = "",
    val initialBalance: String = "0",
    val loading: Boolean = false,
    val enabled: Boolean = true
)