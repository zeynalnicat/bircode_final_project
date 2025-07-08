package com.example.register.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.components.DTextField
import com.example.common.theme.Primary
import com.example.core.AppStrings



@Composable
fun SignUpScreen(){

    val viewModel = viewModel { SignUpViewModel() }
    val state = viewModel.state.collectAsState().value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(Primary).fillMaxSize()
    ) {

        Text(AppStrings.createAccount)

        Column {
            DTextField(
                value = state.email,
                onChange = {viewModel.onIntent(SignUpIntent.OnChangeEmail(it))},
                placeHolder = AppStrings.emailPlaceHolder
            )

            Spacer(Modifier.height(16.dp))
            DTextField(
                value = state.name,
                onChange = {viewModel.onIntent(SignUpIntent.OnChangeName(it))},
                placeHolder = AppStrings.namePlaceHolder
            )

            Spacer(Modifier.height(16.dp))
            DTextField(
                value = state.password,
                onChange = {viewModel.onIntent(SignUpIntent.OnChangePassword(it))},
                placeHolder = AppStrings.passwordPlaceHolder,
                visualTransformation = PasswordVisualTransformation()
            )

        }



    }
}