package com.example.newcard.presentation

sealed class NewCardIntent {
    data class OnChangeColor(val color:String): NewCardIntent()
    data class OnChangeName(val name:String): NewCardIntent()
    data class OnChangeDeposit(val deposit:String): NewCardIntent()

    data object OnHandleSubmit: NewCardIntent()
}