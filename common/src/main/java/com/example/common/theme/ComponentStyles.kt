package com.example.common.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color


object DTextStyle {

    val title = TextStyle(
        fontWeight = FontWeight.W800,
        fontSize = 20.sp,
        color = Color.White
    )

    val link = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        color = Secondary
    )
}