package com.example.payoperation.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.common.domain.CardModel
import com.example.common.presentation.components.BankCard
import com.example.common.presentation.components.CoreTextField
import com.example.common.presentation.components.DButton
import com.example.common.presentation.components.DTopBar
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Secondary
import com.example.core.AppStrings

@Composable

fun PayOperationScreen(
    navController: NavController,
    viewModel: PayOperationViewModel,
    transactionType: String,
    isTopUp: Boolean
) {

    val expanded = remember { mutableStateOf(false) }

    val state = viewModel.state.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
        viewModel.onIntent(PayOperationIntent.OnSetIsTopUp(isTopUp))
        viewModel.onIntent(PayOperationIntent.OnGetCards)
        viewModel.onIntent(PayOperationIntent.OnSetTransactionType(transactionType))
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when(it){

                is PayOperationUiEffect.OnShowError ->snackbarHostState.showSnackbar(it.message)
            }
        }
    }

    Scaffold(
        snackbarHost = {SnackbarHost(snackbarHostState)},
        topBar = {
            DTopBar(
                AppStrings.payment
            ) {
                viewModel.onIntent(PayOperationIntent.OnNavigateBack)
            }
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(AppStrings.selectCard, style = DTextStyle.t14Primary)

            if (state.cards.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val selectedCard =
                        state.cards.find { it.cardId == state.selectedCardId } ?: CardModel(
                            "",
                            "",
                            Secondary.value.toString(),
                            "0",
                            ""
                        )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .background(
                                Color.White
                            )
                            .clickable {
                                expanded.value = !expanded.value
                            }
                    ) {
                        BankCard(
                            cardHolder = selectedCard.cardHolder,
                            cardColor = selectedCard.cardColor.toULong(),
                            cardNumber = selectedCard.cardNumber,
                            availableBalance = selectedCard.availableBalance,
                            isDropDownItem = true
                        )

                    }

                    DropdownMenu(
                        modifier = Modifier.fillMaxWidth(),
                        expanded = expanded.value,
                        onDismissRequest = {
                            expanded.value = !expanded.value
                        }
                    ) {
                        state.cards.forEach { card ->
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .fillMaxWidth()
                                    .background(
                                        if (card.cardId == state.selectedCardId) Color.White.copy(
                                            alpha = 0.4f
                                        ) else Color.White
                                    )
                                    .clickable {
                                        expanded.value = !expanded.value
                                        viewModel.onIntent(PayOperationIntent.OnSetCard(card.cardId))
                                    }
                            ) {

                                BankCard(
                                    cardHolder = card.cardId,
                                    cardNumber = card.cardNumber,
                                    cardColor = card.cardColor.toULong(),
                                    height = 150.dp,
                                    availableBalance = card.availableBalance,
                                    scale = 0.3f,
                                    isDropDownItem = true
                                )

                                Spacer(Modifier.width(12.dp))
                                Text("\$${card.availableBalance}", style = DTextStyle.t14Primary)

                            }
                        }

                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            Color.White
                        )
                        .clickable {
                            viewModel.onIntent(PayOperationIntent.OnNavigateToNewCard)
                        }
                ) {

                    BankCard(
                        cardHolder = "",
                        cardNumber = "****",
                        scale = 0.3f,
                        isDropDownItem = true,
                        isPreview = true
                    )





                }

            }

          Text(AppStrings.amount, style = DTextStyle.t14Primary)


            CoreTextField(
                value = state.amount,
                onChange = {viewModel.onIntent(PayOperationIntent.OnSetAmount(it))},
                placeHolder = AppStrings.amount,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )

            if(state.error.isNotEmpty()){
                Text(state.error, style = DTextStyle.error)

            }
            Spacer(Modifier.height(16.dp))

            DButton(
                title = AppStrings.pay,
                loading = state.isLoading,
                onClick = {viewModel.onIntent(PayOperationIntent.OnHandleSubmit)}
            )

        }
    }
}