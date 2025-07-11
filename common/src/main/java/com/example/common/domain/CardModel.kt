package com.example.common.domain

data class CardModel(
    val cardId:String,
    val cardHolder:String,
    val cardColor: String,
    val availableBalance:String,
    val cardNumber:String,
)