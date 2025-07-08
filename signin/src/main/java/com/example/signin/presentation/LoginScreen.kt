package com.example.signin.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.common.components.DButton
import com.example.common.components.DTextField
import com.example.common.theme.DTextStyle
import com.example.common.theme.Primary
import com.example.core.AppStrings
import kotlin.text.ifEmpty

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController,viewModel: LoginViewModel){

    val snackbarHostState = remember { SnackbarHostState() }



    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when(it){
                is LoginUiEffect.OnShowError -> snackbarHostState.showSnackbar(it.message)
            }
        }
    }

    val state = viewModel.state.collectAsState().value


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(Primary).fillMaxSize().padding(horizontal = 32.dp)
        ) {

            Text(AppStrings.login, style = DTextStyle.title)

            Spacer(Modifier.height(24.dp))
            Column {
                DTextField(
                    value = state.email,
                    onChange = {viewModel.onIntent(LoginIntent.OnChangeEmail(it))},
                    placeHolder = AppStrings.emailPlaceHolder,
                    isError = state.emailError.isNotEmpty(),
                    supportingText = state.emailError.ifEmpty { null }
                )

                Spacer(Modifier.height(16.dp))
                DTextField(
                    value = state.password,
                    onChange = {viewModel.onIntent(LoginIntent.OnChangePassword(it))},
                    placeHolder = AppStrings.passwordPlaceHolder,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = state.passwordError.isNotEmpty(),
                    supportingText = state.passwordError.ifEmpty { null }
                )

                Spacer(Modifier.height(16.dp))

                Text(AppStrings.notHaveAccount, style = DTextStyle.link, modifier = Modifier.clickable{viewModel.onIntent(
                    LoginIntent.OnNavigateToRegister)})

                Spacer(Modifier.height(16.dp))

                DButton(
                    title = AppStrings.signIn,
                    onClick = {viewModel.onIntent(LoginIntent.OnSubmit)},
                    loading = state.loading
                )




            }



        }
    }

}