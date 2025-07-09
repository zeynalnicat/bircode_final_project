package com.example.pin.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.theme.DTextStyle
import com.example.common.theme.Primary


@Composable
fun NumberButton(digit:String, onClick: ()->Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.border(2.dp,Primary, shape = RoundedCornerShape(12.dp)).size(64.dp).clickable{
            onClick()
        }
    ){
        Text(digit, style = DTextStyle.digit)
    }
}