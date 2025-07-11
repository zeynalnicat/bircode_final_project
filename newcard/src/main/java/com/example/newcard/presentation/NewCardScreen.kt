package com.example.newcard.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.common.presentation.components.BankCard
import com.example.common.presentation.components.CoreTextField
import com.example.common.presentation.components.DButton
import com.example.common.presentation.components.DTextField
import com.example.common.presentation.components.DTopBar
import com.example.common.presentation.theme.Blue
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Gray
import com.example.common.presentation.theme.Green
import com.example.common.presentation.theme.Pink40
import com.example.common.presentation.theme.Primary
import com.example.common.presentation.theme.PurpleGrey40
import com.example.common.presentation.theme.Secondary
import com.example.common.presentation.theme.Yellow
import com.example.core.AppStrings

@Composable
fun NewCardScreen(navController: NavController, viewModel: NewCardViewModel) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                is NewCardUiEffect.OnShowError -> snackbarHostState.showSnackbar(it.message)
            }

        }
    }


    val coloredItems = listOf(Pink40, Gray, Secondary, Primary, Green, Blue, PurpleGrey40, Yellow)

    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            DTopBar(AppStrings.newCardTitle) { viewModel.onIntent(NewCardIntent.OnNavigateBack) }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            BankCard(
                cardHolder = state.name,
                cardColor = state.color.toULong(),
                availableBalance = state.initialBalance,
                cardNumber = "****"
            )

            Spacer(Modifier.height(16.dp))

            LazyRow {
                items(coloredItems.size) { index ->
                    Card(
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(32.dp),
                        border = if (Color(state.color.toULong()) == coloredItems[index])
                            BorderStroke(width = 3.dp, color = Color.White)
                        else null,
                        onClick = {
                            viewModel.onIntent(
                                NewCardIntent.OnChangeColor(
                                    coloredItems[index].value.toString()
                                )
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(coloredItems[index])
                        )


                    }

                }
            }
            Spacer(Modifier.height(32.dp))

            CoreTextField(
                value = state.name,
                placeHolder = AppStrings.nameOnCard,
                onChange = { viewModel.onIntent(NewCardIntent.OnChangeName(it)) }
            )

            Spacer(Modifier.height(16.dp))

            CoreTextField(
                value = state.initialBalance,
                placeHolder = AppStrings.initialDeposit,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onChange = { viewModel.onIntent(NewCardIntent.OnChangeDeposit(it)) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            DButton(
                loading = state.loading,
                title = AppStrings.buy,
                onClick = { viewModel.onIntent(NewCardIntent.OnHandleSubmit) }
            )
        }
    }

}