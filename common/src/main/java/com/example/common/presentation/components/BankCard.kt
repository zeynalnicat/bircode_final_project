package com.example.common.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Secondary
import com.example.core.AppStrings


@Composable
fun BankCard(
    cardHolder: String,
    cardNumber: String,
    availableBalance: String = "",
    isPreview: Boolean = false,
    previewAction: ()->Unit = {},
    cardColor: ULong = Secondary.value,
    contentColor: Color = Color.White,
    scale: Float = 1f,
    width: Dp = 300.dp,
    height: Dp = 200.dp,
) {

    if(isPreview){
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp).size(width,height)
                .clickable {
                    previewAction()
                },
            contentAlignment = Alignment.Center

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = 0.9f
                        shadowElevation = 8.dp.toPx()
                        shape = RoundedCornerShape(20.dp)
                        clip = true
                    }
                    .blur(16.dp)
            ) {
                BankCardDetails(
                    availableBalance = "0",
                    cardNumber = cardNumber,
                    cardHolder = cardHolder,
                    cardColor = cardColor,
                    contentColor = contentColor,
                    width = width,
                    height = height,
                    scale = scale
                )
            }

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        previewAction()
                    }
            )
        }
    }else{
       BankCardDetails(
           availableBalance = availableBalance,
           cardNumber = cardNumber,
           cardHolder = cardHolder,
           cardColor = cardColor,
           contentColor = contentColor,
           width = width,
           height = height,
           scale = scale
       )
    }


}

@Composable
private fun BankCardDetails(
    availableBalance:String,
    cardNumber:String,
    cardHolder:String,
    cardColor: ULong,
    contentColor:Color,
    width: Dp,
    height:Dp,
    scale:Float,


){

    Card(
    colors = CardColors(
        containerColor = Color(cardColor),
        contentColor = contentColor,
        disabledContainerColor = Color(cardColor),
        disabledContentColor = contentColor,
    ),
    modifier = Modifier
        .size(width = width, height = height)
        .border(
            1.dp, Color.Transparent,
            RoundedCornerShape(10.dp)
        ).scale(scale)
        .size(width,height)
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 40.dp, end = 25.dp, top = 30.dp, bottom = 30.dp),
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(AppStrings.availableBalance, style = DTextStyle.t12)
                Spacer(Modifier.height(10.dp))
                Text("\$$availableBalance ", style = DTextStyle.title.copy(color = Color.White))
            }


            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(com.example.common.R.drawable.card_censor),
                contentDescription = "",
            )

        }
        val digitNumbers = "**** ".repeat(3) + cardNumber.takeLast(4)
        Text(digitNumbers, style = DTextStyle.t12)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(AppStrings.cardHolder, style = DTextStyle.t12)
                Spacer(Modifier.height(10.dp))
                Text(cardHolder, style = DTextStyle.title.copy(color = Color.White, fontSize = 14.sp))
            }
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(com.example.common.R.drawable.card_type),
                contentDescription = ""
            )
        }

    }
}}
