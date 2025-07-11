package com.example.home.domain

import com.example.common.domain.TransactionModel
import com.example.core.Result
import javax.inject.Inject

class HomeGetCardTransactionsUseCase @Inject constructor(private val repository: HomeRepository) {

    suspend operator fun invoke(cardId: String): Result<List<TransactionModel>> {
        return repository.getCardTransactions(cardId = cardId)
    }
}