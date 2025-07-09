package com.example.newcard.domain

import com.example.core.Result

interface NewCardRepository {

    suspend fun createNewCard(cardHolder:String,cardColor:String,deposit:String,cardNumber:String): Result<Unit>
}