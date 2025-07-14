package com.example.payoperation.domain

import com.example.core.Result
import javax.inject.Inject

class PayOperationSaveTransactionUseCase @Inject constructor(private val repository: PayOperationRepository) {

    suspend operator fun invoke(
        cardId: String,
        amount: String,
        transactionName: String
    ): Result<String> {
        return repository.saveTransaction(cardId,amount,transactionName)
    }

}