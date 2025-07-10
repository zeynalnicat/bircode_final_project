package com.example.paybill.presentation

sealed class PayBillIntent {

    data object OnNavigateBack: PayBillIntent()
    data class OnChangeBillType(val type:String): PayBillIntent()
    data class OnChangeAmount(val amount:String): PayBillIntent()
}