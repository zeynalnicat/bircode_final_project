package com.example.payoperation.presentation

sealed class PayOperationUiEffect {

    data class OnShowError(val message:String): PayOperationUiEffect()
}