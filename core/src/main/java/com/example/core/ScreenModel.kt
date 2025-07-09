package com.example.core

sealed class ScreenModel(val route:String) {

    data object Home: ScreenModel(route = "Home")
    data object SignUp: ScreenModel(route = "SignUp")
    data object Login:ScreenModel(route = "Login")
    data object Settings: ScreenModel(route = "Settings")
    data object Profile: ScreenModel(route = "Profile")
    data object Pin: ScreenModel(route = "Pin")

    data object NewCard: ScreenModel(route = "NewCard")
}