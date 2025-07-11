package com.example.payoperation.presentation

sealed class PayOperationIntent {

    data object OnNavigateBack: PayOperationIntent()

    data class OnSetTransactionType(val transactionType:String): PayOperationIntent()
    data class OnSetCard(val card: String): PayOperationIntent()

    data object OnGetCards : PayOperationIntent()

    data object OnNavigateToNewCard: PayOperationIntent()

    data class OnSetAmount(val amount:String): PayOperationIntent()

    data object OnHandleSubmit: PayOperationIntent()

}