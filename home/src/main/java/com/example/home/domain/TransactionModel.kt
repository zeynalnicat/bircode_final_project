package com.example.home.domain

data class TransactionModel(
    val id:String,
    val cardId:String,
    val userId:String,
    val date:String,
    val isExpense: Boolean,
    val amount:String,
    val transactionName:String,
)