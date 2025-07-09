package com.example.home.domain

import com.example.core.Result
import javax.inject.Inject

class GetUserCardsUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(): Result<List<CardModel>>{
        return homeRepository.getUserCards()
    }
}