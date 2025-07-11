package com.example.carddetails.domain

import com.example.common.domain.CardModel
import com.example.core.Result
import javax.inject.Inject

class CardDetailsGetCardUseCase @Inject constructor(private val repository: CardDetailsRepository) {

    suspend operator fun invoke(cardId: String): Result<CardModel> {
        return repository.getCardDetails(cardId)
    }
}