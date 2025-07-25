package com.example.carddetails.presentation

sealed class CardDetailIntent {

    data object OnNavigateBack: CardDetailIntent()

    data class OnNavigateToTransactionDetails(val id:String): CardDetailIntent()

    data class OnGetCardDetails(val cardId:String): CardDetailIntent()

    data class OnGetCardTransactions(val cardId:String): CardDetailIntent()
}