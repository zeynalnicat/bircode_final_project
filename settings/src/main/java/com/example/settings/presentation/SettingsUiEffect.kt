package com.example.settings.presentation

sealed class SettingsUiEffect {

    data class OnShowError(val message:String): SettingsUiEffect()
}