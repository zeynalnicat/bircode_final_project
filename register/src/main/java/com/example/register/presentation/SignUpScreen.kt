package com.example.register.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.common.components.DButton
import com.example.common.components.DTextField
import com.example.common.theme.DTextStyle
import com.example.common.theme.Primary
import com.example.core.AppStrings



@Composable
fun SignUpScreen(navController: NavController){

    val viewModel = viewModel { SignUpViewModel(navController) }
    val state = viewModel.state.collectAsState().value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(Primary).fillMaxSize().padding(horizontal = 32.dp)
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
                title = AppStrings.register,
                onClick = {viewModel.onIntent(SignUpIntent.OnSubmit)},
                loading = state.loading
            )




        }



    }
}