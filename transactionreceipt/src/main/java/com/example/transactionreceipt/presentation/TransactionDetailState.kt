package com.example.transactionreceipt.presentation


data class TransactionDetailState (
    val id:String = "",
    val amount: String = "",
    val transactionName :String = "",
    val name : String = "",
    val isExpense : Boolean = false,
    val date: String = "",
)