package com.example.transactionreceipt.domain

import com.example.common.domain.TransactionModel
import com.example.core.Result
import javax.inject.Inject

class GetTransactionDetailsUseCase @Inject constructor(private val repository: TransactionDetailRepository) {

    suspend operator fun invoke(id: String): Result<TransactionModel> {
        return repository.getTransactionDetail(id)
    }
}