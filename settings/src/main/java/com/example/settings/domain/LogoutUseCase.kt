package com.example.settings.domain

import com.example.core.Result
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: SettingsRepository) {

    suspend operator fun invoke(): Result<Unit> {
        return repository.onLogOut()
    }
}