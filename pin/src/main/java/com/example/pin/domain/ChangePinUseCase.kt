package com.example.pin.domain

import com.example.core.Result
import javax.inject.Inject

class ChangePinUseCase @Inject constructor(private val repository: PinRepository) {

    suspend operator fun invoke(newPin: String): Result<Unit> {
        return repository.changePin(newPin)
    }
}