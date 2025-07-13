package com.example.transactionreceipt.presentation

sealed class TransactionDetailIntent{

    data object OnNavigateBack: TransactionDetailIntent()

    data class OnSetTransactionId(val id:String): TransactionDetailIntent()

    data object OnFetchTransactionDetails: TransactionDetailIntent()


}