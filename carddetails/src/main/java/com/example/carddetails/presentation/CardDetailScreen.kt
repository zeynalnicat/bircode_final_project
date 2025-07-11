package com.example.carddetails.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.common.presentation.components.BankCard
import com.example.common.presentation.components.DTopBar
import com.example.common.presentation.components.RecentTransactions
import com.example.core.AppStrings

@Composable
fun CardDetailsScreen(navController: NavController, viewModel: CardDetailViewModel, id: String) {

    val state = viewModel.state.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    val scrollState = rememberScrollState()


    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
        viewModel.onIntent(CardDetailIntent.OnGetCardDetails(id))
        viewModel.onIntent(CardDetailIntent.OnGetCardTransactions(cardId = id))
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                is CardDetailUiEffect.OnShowError -> snackBarHostState.showSnackbar(it.message)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            DTopBar(
                AppStrings.cardDetail
            ) {
                viewModel.onIntent(CardDetailIntent.OnNavigateBack)
            }
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            BankCard(
                cardHolder = state.card.cardHolder,
                cardNumber = state.card.cardNumber,
                cardColor = state.card.cardColor.toULong(),
                availableBalance = state.card.availableBalance
            )

            RecentTransactions(
                state.transactions
            )
        }
    }
}