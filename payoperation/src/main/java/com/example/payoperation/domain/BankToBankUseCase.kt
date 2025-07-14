package com.example.payoperation.domain

import com.example.core.Result
import javax.inject.Inject

class BankToBankUseCase @Inject constructor(private val repository: PayOperationRepository) {

    suspend operator fun invoke(
        cardId: String,
        amount: String,
        transactionName: String,
        receiverCardNumber: String
    ): Result<String> {
        return repository.transferTo(cardId, amount, transactionName, receiverCardNumber)
    }
}