package com.example.carddetails.presentation

import com.example.common.domain.CardModel
import com.example.common.domain.TransactionModel
import com.example.common.presentation.theme.Secondary

data class CardDetailsState(
    val card: CardModel = CardModel("", "", Secondary.value.toString(), "", ""),
    val transactions: List<TransactionModel> = emptyList()
)