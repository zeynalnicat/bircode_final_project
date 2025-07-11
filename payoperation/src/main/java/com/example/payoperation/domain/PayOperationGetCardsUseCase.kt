package com.example.payoperation.domain

import com.example.common.domain.CardModel
import com.example.core.Result
import javax.inject.Inject

class PayOperationGetCardsUseCase @Inject constructor(private val payOperationRepository: PayOperationRepository) {

    suspend operator fun invoke(): Result<List<CardModel>>{
        return payOperationRepository.getCards()
    }
}