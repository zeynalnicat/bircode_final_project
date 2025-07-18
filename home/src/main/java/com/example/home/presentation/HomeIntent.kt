package com.example.home.presentation

sealed class HomeIntent {

    data object OnNavigateToAddCard: HomeIntent()
    data object OnNavigateToProfile: HomeIntent()

    data object OnNavigateToPayBill: HomeIntent()

    data object OnNavigateToPayOperation:HomeIntent()

    data object OnNavigateToBankToBank: HomeIntent()

    data class OnNavigateToTransactionDetails(val id:String): HomeIntent()

    data class OnNavigateToCardDetails(val id:String): HomeIntent()

    data object OnGetUserCards: HomeIntent()

    data class OnSwipePager(val p1:Int): HomeIntent()

    data object OnGetCardTransactions: HomeIntent()



}