package com.example.bircodefinal.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.carddetails.presentation.CardDetailsScreen
import com.example.core.ScreenModel
import com.example.home.presentation.HomeScreen
import com.example.newcard.presentation.NewCardScreen
import com.example.paybill.presentation.PayBillScreen
import com.example.payoperation.presentation.PayOperationScreen
import com.example.pin.presentation.PinScreen
import com.example.profile.presentation.ProfileScreen
import com.example.register.presentation.SignUpScreen
import com.example.settings.presentation.SettingsScreen
import com.example.signin.presentation.LoginScreen
import com.example.transactionreceipt.presentation.TransactionDetailScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigator(
    innerPadding: PaddingValues,
    navController: NavHostController,
    firebaseAuth: FirebaseAuth
) {


    NavHost(
        navController = navController,
        startDestination = if (firebaseAuth.currentUser != null) ScreenModel.Pin.withIsChangePin(false) else ScreenModel.SignUp.route,
        modifier = Modifier.padding(innerPadding)
    ) {

        composable(ScreenModel.Home.route) { HomeScreen(navController, hiltViewModel()) }
        composable(ScreenModel.SignUp.route) { SignUpScreen(navController, hiltViewModel()) }
        composable(ScreenModel.Login.route) { LoginScreen(navController, hiltViewModel()) }
        composable(ScreenModel.Settings.route) { SettingsScreen(navController, hiltViewModel()) }
        composable(ScreenModel.Pin.route, arguments = listOf(navArgument("isChangePin") {
            type =
                NavType.BoolType
        })) { backStackEntry ->
            val isChangePin = backStackEntry.arguments?.get("isChangePin") as? Boolean ?: false
            PinScreen(navController, hiltViewModel(), isChangePin)
        }
        composable(ScreenModel.Profile.route) { ProfileScreen(navController, hiltViewModel()) }
        composable(ScreenModel.NewCard.route) { NewCardScreen(navController, hiltViewModel()) }
        composable(ScreenModel.PayBill.route) { PayBillScreen(navController, hiltViewModel()) }
        composable(
            ScreenModel.PayOperation.route,
            arguments = listOf(
                navArgument("transactionType") { type = NavType.StringType },
                navArgument("isTopUp") {
                    type =
                        NavType.BoolType
                },
                navArgument("isBankToBank") {
                    type = NavType.BoolType
                })
        ) { backStackEntry ->
            val transactionType = backStackEntry.arguments?.get("transactionType") as? String ?: ""
            val isTopUp = backStackEntry.arguments?.get("isTopUp") as? Boolean ?: false
            val isBankToBank = backStackEntry.arguments?.get("isBankToBank") as? Boolean ?: false
            PayOperationScreen(
                navController,
                hiltViewModel(),
                transactionType,
                isTopUp,
                isBankToBank
            )
        }

        composable(ScreenModel.CardDetails.route, arguments = listOf(navArgument("id") {
            type =
                NavType.StringType
        })) { backStackEntry ->
            val id = backStackEntry.arguments?.get("id") as? String ?: ""
            CardDetailsScreen(navController, hiltViewModel(), id)
        }

        composable(ScreenModel.TransactionDetails.route, arguments = listOf(navArgument("id") {
            type = NavType.StringType
        })) { backStackEntry ->
            val id = backStackEntry.arguments?.get("id") as? String ?: ""
            TransactionDetailScreen(navController, hiltViewModel(), id)
        }


    }
}