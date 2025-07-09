package com.example.home.presentation

sealed class HomeIntent {

    data object OnNavigateToAddCard: HomeIntent()

    data object OnGetUserCards: HomeIntent()
}