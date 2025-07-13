package com.example.pin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Primary
import com.example.common.presentation.theme.Secondary
import com.example.core.AppStrings
import com.example.pin.presentation.components.NumberButton


@Composable
fun PinScreen(navController: NavController, viewModel: PinViewModel) {


    LaunchedEffect(Unit) {
        viewModel.onIntent(PinIntent.OnGetName)
        viewModel.initiateController(navController)
    }

    val state = viewModel.state.collectAsState().value
    var kt = -1


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 48.dp)
        ) {
            Text(
                text = "Welcome",
                color = Primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.name,
                color = Primary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )
        }


        Text(AppStrings.enterPin, style = DTextStyle.title)

        Spacer(Modifier.height(16.dp))


        Row {
            state.pin.forEachIndexed { index, digit ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .border(
                            2.dp,
                            if (state.currentIndex == index) Secondary else Secondary.copy(alpha = 0.3f),
                            RoundedCornerShape(12.dp)
                        )
                        .size(40.dp)
                ) {
                    Text(if (digit.isNotEmpty()) "*" else "", style = DTextStyle.digit)
                }
                Spacer(Modifier.width(16.dp))
            }

        }

        if(state.error.isNotEmpty()){

            Spacer(Modifier.height(16.dp))
            Text(state.error, style = DTextStyle.error)
        }

        Spacer(Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 10.dp, bottomEnd = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Primary.copy(alpha = 0.1f)
                )
            ) {
                for (i in 0..2) {
                    kt += 2
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (k in 0..2) {
                            var digit = i + k + kt
                            NumberButton(digit.toString()) {
                                viewModel.onIntent(
                                    PinIntent.OnPressDigit(
                                        digit.toString()
                                    )
                                )
                            }
                        }
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NumberButton("Clear") { viewModel.onIntent(PinIntent.OnClear) }
                    NumberButton("0") { viewModel.onIntent(PinIntent.OnPressDigit("0")) }
                    NumberButton("⌫") { viewModel.onIntent(PinIntent.OnRemoveDigit) }
                }
        }


        }


    }
}