package com.example.settings.domain

import com.example.core.Result

interface SettingsRepository
{
    suspend fun onLogOut() : Result<Unit>
}