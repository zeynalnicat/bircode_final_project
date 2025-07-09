package com.example.home.presentation

import com.example.home.domain.CardModel

data class HomeState(
    val imgUri:String = "",
    val cards: List<CardModel> = emptyList(),
    val currentCardIndex:Int = 0,

)