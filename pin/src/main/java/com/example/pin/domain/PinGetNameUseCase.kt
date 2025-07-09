package com.example.pin.domain

import com.example.core.Result
import javax.inject.Inject

class PinGetNameUseCase @Inject constructor(private val repository: PinRepository) {

    suspend operator fun invoke(): Result<String>{
        return repository.getName()
    }
}