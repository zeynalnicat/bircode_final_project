package com.example.carddetails.domain

import com.example.common.domain.CardModel
import com.example.common.domain.TransactionModel
import com.example.core.Result

interface CardDetailsRepository {

    suspend fun getCardDetails(cardId:String): Result<CardModel>

    suspend fun getCardTransactions(cardId:String):Result<List<TransactionModel>>
}