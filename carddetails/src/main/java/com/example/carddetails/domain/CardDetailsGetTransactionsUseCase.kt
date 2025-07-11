package com.example.carddetails.domain

import com.example.common.domain.TransactionModel
import com.example.core.Result
import javax.inject.Inject

class CardDetailsGetTransactionsUseCase @Inject constructor(private val repository: CardDetailsRepository) {

    suspend operator fun invoke(cardId: String): Result<List<TransactionModel>> {
        return repository.getCardTransactions(cardId = cardId)
    }
}