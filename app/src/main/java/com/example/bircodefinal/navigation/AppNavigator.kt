package com.example.bircodefinal.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.home.presentation.HomeScreen
import com.example.pin.presentation.PinScreen
import com.example.profile.presentation.ProfileScreen
import com.example.register.presentation.SignUpScreen
import com.example.settings.presentation.SettingsScreen
import com.example.signin.presentation.LoginScreen

@Composable
fun AppNavigator(innerPadding: PaddingValues, navController: NavHostController){


    NavHost(
        navController = navController,
        startDestination = ScreenModel.SignUp.route,
        modifier = Modifier.padding(innerPadding)
    ){

        composable(ScreenModel.Home.route){ HomeScreen() }
        composable( ScreenModel.SignUp.route) { SignUpScreen() }
        composable(ScreenModel.Login.route) { LoginScreen() }
        composable(ScreenModel.Settings.route) { SettingsScreen() }
        composable(ScreenModel.Pin.route) { PinScreen() }
        composable(ScreenModel.Profile.route) { ProfileScreen() }

    }
}