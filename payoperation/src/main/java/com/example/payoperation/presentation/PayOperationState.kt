package com.example.payoperation.presentation

import com.example.common.domain.CardModel

data class PayOperationState(
    val transactionType:String = "",
    val amount:String = "0",
    val cards: List<CardModel> = emptyList(),
    val selectedCardId: String = "",
    val isLoading: Boolean = false,
    val error :String = "",

)
