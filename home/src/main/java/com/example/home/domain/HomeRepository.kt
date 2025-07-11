package com.example.home.domain

import com.example.common.domain.CardModel
import com.example.common.domain.TransactionModel
import com.example.core.Result

interface HomeRepository {

    suspend fun getUserCards(): Result<List<CardModel>>

    suspend fun getCardTransactions(cardId:String): Result<List<TransactionModel>>
}