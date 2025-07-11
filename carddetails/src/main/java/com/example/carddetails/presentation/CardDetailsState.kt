package com.example.carddetails.presentation

import com.example.common.domain.CardModel
import com.example.common.domain.TransactionModel

data class CardDetailsState(
    val card: CardModel = CardModel("", "", "", "", ""),
    val transactions: List<TransactionModel> = emptyList()
)