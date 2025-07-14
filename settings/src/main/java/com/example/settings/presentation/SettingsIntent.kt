package com.example.settings.presentation

sealed class SettingsIntent {

    data object OnNavigateToProfile : SettingsIntent()

    data object OnLogOut: SettingsIntent()
}