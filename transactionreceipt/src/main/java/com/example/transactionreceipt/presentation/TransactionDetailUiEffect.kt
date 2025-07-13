package com.example.transactionreceipt.presentation

sealed class TransactionDetailUiEffect {

    data class OnShowError(val message:String): TransactionDetailUiEffect()
}