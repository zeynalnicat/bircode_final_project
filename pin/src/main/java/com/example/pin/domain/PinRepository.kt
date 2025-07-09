package com.example.pin.domain

import com.example.core.Result

interface PinRepository {

    suspend fun getName(): Result<String>

    suspend fun enterPin(pin:String): Result<Unit>
}