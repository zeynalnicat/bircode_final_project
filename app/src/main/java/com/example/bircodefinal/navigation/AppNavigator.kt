package com.example.bircodefinal.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.ScreenModel
import com.example.home.presentation.HomeScreen
import com.example.newcard.presentation.NewCardScreen
import com.example.paybill.presentation.PayBillScreen
import com.example.pin.presentation.PinScreen
import com.example.profile.presentation.ProfileScreen
import com.example.register.presentation.SignUpScreen
import com.example.settings.presentation.SettingsScreen
import com.example.signin.presentation.LoginScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigator(innerPadding: PaddingValues, navController: NavHostController,firebaseAuth: FirebaseAuth){


    NavHost(
        navController = navController,
        startDestination = if(firebaseAuth.currentUser!=null) ScreenModel.Pin.route else ScreenModel.SignUp.route,
        modifier = Modifier.padding(innerPadding)
    ){

        composable(ScreenModel.Home.route){ HomeScreen(navController,hiltViewModel()) }
        composable( ScreenModel.SignUp.route) { SignUpScreen(navController,hiltViewModel()) }
        composable(ScreenModel.Login.route) { LoginScreen(navController,hiltViewModel()) }
        composable(ScreenModel.Settings.route) { SettingsScreen() }
        composable(ScreenModel.Pin.route) { PinScreen(navController,hiltViewModel()) }
        composable(ScreenModel.Profile.route) { ProfileScreen(navController,hiltViewModel()) }
        composable(ScreenModel.NewCard.route) { NewCardScreen(navController,hiltViewModel()) }
        composable(ScreenModel.PayBill.route) { PayBillScreen(navController,hiltViewModel()) }

    }
}