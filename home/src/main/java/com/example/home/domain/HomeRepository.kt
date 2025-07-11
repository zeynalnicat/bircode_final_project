package com.example.home.domain

import com.example.common.domain.CardModel
import com.example.core.Result

interface HomeRepository {

    suspend fun getUserCards(): Result<List<CardModel>>
}