package com.example.transactionreceipt.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.common.presentation.components.DTopBar
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Gray10
import com.example.common.presentation.theme.Primary
import com.example.common.presentation.theme.Red10
import com.example.common.presentation.theme.TextFieldGray
import com.example.core.AppStrings

@Composable
fun TransactionDetailScreen(navController: NavController, viewModel: TransactionDetailViewModel, id: String) {

    val state = viewModel.state.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
        viewModel.onIntent(TransactionDetailIntent.OnSetTransactionId(id))
        viewModel.onIntent(TransactionDetailIntent.OnFetchTransactionDetails)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                is TransactionDetailUiEffect.OnShowError -> snackBarHostState.showSnackbar(it.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            DTopBar(AppStrings.transactionDetail) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .background(Gray10, shape = CircleShape)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = if (state.isExpense) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Transaction Icon",
                    tint = Primary,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .background(TextFieldGray, shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = state.transactionName, style = DTextStyle.t16Primary)
                Text(text = state.name, style = DTextStyle.t14Gray)

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .background(Red10.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text("Transactions Status: Unpaid", color = Red10, style = DTextStyle.t14Primary)
                }

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "$${state.amount} ",
                    style = DTextStyle.t14Primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(AppStrings.billNumber, style = DTextStyle.t14Gray)
                    Text(state.id, style = DTextStyle.t14Primary)
                }

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(thickness = 1.dp, color = Gray10)

                Spacer(modifier = Modifier.height(8.dp))

                // Due Date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(AppStrings.dueDate, style = DTextStyle.t14Gray)
                    Text(state.date, style = DTextStyle.t14Primary)
                }
            }
        }
    }
}