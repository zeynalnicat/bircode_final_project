package com.example.settings.presentation

sealed class SettingsIntent {

    data object OnNavigateToProfile : SettingsIntent()

    data object OnNavigateToPin: SettingsIntent()

    data object OnLogOut: SettingsIntent()
}