package com.example.pin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.common.theme.DTextStyle
import com.example.common.theme.Primary
import com.example.core.AppStrings


@Composable
fun PinScreen(){

    Column(
        modifier = Modifier.fillMaxSize().background(Primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(AppStrings.enterPin, style = DTextStyle.title)

    }
}