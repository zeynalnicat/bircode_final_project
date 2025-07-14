package com.example.register.presentation

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
import androidx.navigation.NavController
import com.example.common.presentation.components.DButton
import com.example.common.presentation.components.DTextField
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Primary
import com.example.core.AppStrings


@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel){

    val state = viewModel.state.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
    }

    LaunchedEffect(viewModel.effect) {

        viewModel.effect.collect {
            when(it){
                is SignUpUiEffect.OnShowError -> snackbarHostState.showSnackbar(it.error)
            }

        }

    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(Primary).fillMaxSize().padding(horizontal = 32.dp).padding(paddingValues)
        ) {

            Text(AppStrings.createAccount, style = DTextStyle.title)

            Spacer(Modifier.height(24.dp))
            Column {
                DTextField(
                    value = state.email,
                    onChange = {viewModel.onIntent(SignUpIntent.OnChangeEmail(it))},
                    placeHolder = AppStrings.emailPlaceHolder,
                    isError = state.emailError.isNotEmpty(),
                    supportingText = state.emailError.ifEmpty { null }
                )

                Spacer(Modifier.height(16.dp))
                DTextField(
                    value = state.name,
                    onChange = {viewModel.onIntent(SignUpIntent.OnChangeName(it))},
                    placeHolder = AppStrings.namePlaceHolder,
                    isError = state.nameError.isNotEmpty(),
                    supportingText = state.nameError.ifEmpty { null }
                )

                Spacer(Modifier.height(16.dp))
                DTextField(
                    value = state.password,
                    onChange = {viewModel.onIntent(SignUpIntent.OnChangePassword(it))},
                    placeHolder = AppStrings.passwordPlaceHolder,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = state.passwordError.isNotEmpty(),
                    supportingText = state.passwordError.ifEmpty { null }
                )

                Spacer(Modifier.height(16.dp))

                Text(AppStrings.alreadyHaveAccount, style = DTextStyle.link, modifier = Modifier.clickable{viewModel.onIntent(
                    SignUpIntent.OnNavigateToLogin)})

                Spacer(Modifier.height(16.dp))

                DButton(
                    enabled = state.enabled,
                    title = AppStrings.register,
                    onClick = {viewModel.onIntent(SignUpIntent.OnSubmit)},
                    loading = state.loading
                )




            }



        }

    }

}