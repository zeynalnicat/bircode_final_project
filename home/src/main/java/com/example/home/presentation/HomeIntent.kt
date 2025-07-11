package com.example.home.presentation

sealed class HomeIntent {

    data object OnNavigateToAddCard: HomeIntent()
    data object OnNavigateToProfile: HomeIntent()

    data object OnNavigateToPayBill: HomeIntent()

    data object OnGetUserCards: HomeIntent()

    data class OnSwipePager(val p1:Int): HomeIntent()

    data class OnGetCardTransactions(val p1:Int): HomeIntent()

}