package com.example.pin.domain

import com.example.core.Result
import javax.inject.Inject

class EnterPinUseCase @Inject constructor(private val pinRepository: PinRepository) {

    suspend operator fun invoke(pin:String): Result<Unit>{
        return pinRepository.enterPin(pin)
    }
}