package com.example.paybill.presentation

import com.example.common.domain.CardModel

sealed class PayBillIntent {

    data object OnNavigateBack: PayBillIntent()
    data class OnChangeBillType(val type:String): PayBillIntent()
    data class OnChangeAmount(val amount:String): PayBillIntent()

    data object OnNavigateToPayOperation: PayBillIntent()


}