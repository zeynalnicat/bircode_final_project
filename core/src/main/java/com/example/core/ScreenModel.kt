package com.example.core

sealed class ScreenModel(val route: String) {

    data object Home : ScreenModel(route = "home")
    data object SignUp : ScreenModel(route = "signup")
    data object Login : ScreenModel(route = "login")
    data object Settings : ScreenModel(route = "settings")
    data object Profile : ScreenModel(route = "profile")
    data object Pin : ScreenModel(route = "pin/{isChangePin}") {
        fun withIsChangePin(isChangePin: Boolean) = "pin/$isChangePin"
    }

    data object NewCard : ScreenModel(route = "new-card")

    data object PayBill : ScreenModel(route = "pay-bill")

    data object PayOperation :
        ScreenModel(route = "pay-operation/{transactionType}/{isTopUp}/{isBankToBank}") {
        fun withTransactionType(transactionType: String, isTopUp: Boolean, isBankToBank: Boolean) =
            "pay-operation/$transactionType/$isTopUp/$isBankToBank"
    }

    data object CardDetails : ScreenModel(route = "card-details/{id}") {
        fun withId(id: String) = "card-details/$id"
    }

    data object TransactionDetails : ScreenModel(route = "transaction-details/{id}") {
        fun withId(id: String) = "transaction-details/$id"
    }
}