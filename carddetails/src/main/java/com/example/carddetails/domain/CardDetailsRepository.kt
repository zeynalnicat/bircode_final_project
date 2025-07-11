package com.example.carddetails.domain

import com.example.common.domain.CardModel
import com.example.core.Result

interface CardDetailsRepository {

    suspend fun getCardDetails(cardId:String): Result<CardModel>
}