package com.example.core

sealed class ScreenModel(val route:String) {

    data object Home: ScreenModel(route = "home")
    data object SignUp: ScreenModel(route = "signup")
    data object Login:ScreenModel(route = "login")
    data object Settings: ScreenModel(route = "settings")
    data object Profile: ScreenModel(route = "profile")
    data object Pin: ScreenModel(route = "pin")

    data object NewCard: ScreenModel(route = "new-card")

    data object PayBill: ScreenModel(route = "pay-bill")

    data object PayOperation:ScreenModel(route = "pay-operation/{transactionType}/{isTopUp}"){
        fun withTransactionType(transactionType:String,isTopUp: Boolean) = "pay-operation/$transactionType/$isTopUp"
    }
    data object CardDetails: ScreenModel(route = "card-details/{id}"){
        fun withId(id:String) = "card-details/$id"
    }
}