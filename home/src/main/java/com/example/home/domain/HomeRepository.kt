package com.example.home.domain

import com.example.core.Result

interface HomeRepository {

    suspend fun getUserCards(): Result<List<CardModel>>
}