package com.example.newcard.domain

import com.example.core.Result
import javax.inject.Inject

class CreateNewCardUseCase @Inject constructor(private val repository: NewCardRepository) {

    suspend operator fun invoke(cardHolder: String,cardColor:String,deposit:String,cardNumber:String): Result<Unit>{
        return repository.createNewCard(cardHolder,cardColor,deposit, cardNumber =cardNumber )
    }
}