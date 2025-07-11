package com.example.payoperation.domain

import com.example.core.Result
import javax.inject.Inject

class PayOperationOnTopUpUseCase @Inject constructor(private val repository: PayOperationRepository) {

    suspend operator fun invoke(cardId:String,amount:String,transactionName:String): Result<Unit>{
        return repository.topUpBalance(cardId,amount,transactionName)
    }
}