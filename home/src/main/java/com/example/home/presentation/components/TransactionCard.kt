package com.example.home.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Gray
import com.example.common.presentation.theme.Green
import com.example.common.domain.TransactionModel

@Composable
fun TransactionCard(
    transactionModel: TransactionModel
) {


    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.White,
            disabledContentColor= Color.Black
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.border(
                        1.dp,
                        Gray.copy(alpha = 0.4f),
                        RoundedCornerShape(8.dp)
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        if (transactionModel.isExpense) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = "",
                        tint = if (transactionModel.isExpense) Color.Red else Green,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(Modifier.width(8.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        transactionModel.transactionName,
                        style = DTextStyle.t12Bold
                    )

                    Text(
                       transactionModel.date,
                        style = DTextStyle.t12Gray
                    )
                }
            }

            Text(
                "${if (transactionModel.isExpense) '-' else '+'} ${transactionModel.amount}",
                style = DTextStyle.t12.copy(color = if(transactionModel.isExpense) Color.Red else Green)
            )

        }

    }

}