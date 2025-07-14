package com.example.payoperation.domain

import com.example.common.domain.CardModel
import com.example.core.Result

interface PayOperationRepository {

    suspend fun getCards(): Result<List<CardModel>>

    suspend fun saveTransaction(
        cardId: String,
        amount: String,
        transactionName: String
    ): Result<String>

    suspend fun topUpBalance(
        cardId: String,
        amount: String,
        transactionName: String
    ): Result<String>

    suspend fun transferTo(
        cardId: String,
        amount: String,
        transactionName: String,
        receiverCardNumber: String
    ): Result<String>
}