package com.example.home.presentation

import com.example.common.domain.CardModel
import com.example.home.domain.TransactionModel

data class HomeState(
    val imgUri:String = "",
    val cards: List<CardModel> = emptyList(),
    val currentCardIndex:Int = 0,
    val transactions:List<TransactionModel> = emptyList(),

)