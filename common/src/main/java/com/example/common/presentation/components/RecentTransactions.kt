package com.example.common.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.common.presentation.theme.DTextStyle
import com.example.core.AppStrings
import com.example.common.domain.TransactionModel


@Composable
fun RecentTransactions(
    transactions: List<TransactionModel>,
    onClick: (String)->Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.White,
                disabledContentColor = Color.Black
            )

        ) {
            if (transactions.isEmpty()) {
                Column(
                    modifier = Modifier.height(height = 100.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        AppStrings.noRecentTransactions,
                        style = DTextStyle.t12Bold
                    )
                }
            }
            else{
                transactions.forEach {
                        Column(
                            Modifier.padding(8.dp).clickable{
                                onClick(it.id)
                            }
                        ) {
                            TransactionCard(
                                it
                            )
                        }

                    }

                }
            }
        }

    }
