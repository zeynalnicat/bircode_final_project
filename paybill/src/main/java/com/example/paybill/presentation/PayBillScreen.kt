package com.example.paybill.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.common.presentation.components.DButton
import com.example.common.presentation.components.DTopBar
import com.example.common.presentation.theme.DTextStyle
import com.example.core.AppStrings
import com.example.paybill.presentation.components.BillSelector


@Composable
fun PayBillScreen(navController: NavController, viewModel: PayBillViewModel) {

    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
    }

    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            DTopBar(AppStrings.payBill) { viewModel.onIntent(PayBillIntent.OnNavigateBack) }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Text(AppStrings.yourBills, style = DTextStyle.t14Primary)

            Spacer(Modifier.height(16.dp))

            BillSelector(state.billType) { viewModel.onIntent(PayBillIntent.OnChangeBillType(it)) }

            Spacer(Modifier.height(32.dp))

            DButton(
                title = AppStrings.select,
                onClick = {
                    viewModel.onIntent(PayBillIntent.OnNavigateToPayOperation)
                }
            )

        }

    }
}