package com.example.transactionreceipt.domain

import com.example.common.domain.TransactionModel
import com.example.core.Result

interface TransactionDetailRepository {

    suspend fun getTransactionDetail(id:String): Result<TransactionModel>
}