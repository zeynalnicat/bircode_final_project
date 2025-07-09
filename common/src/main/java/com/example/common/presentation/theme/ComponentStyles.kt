package com.example.common.presentation.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color


object DTextStyle {


    val t12 = TextStyle(
        color = Color.White,
        fontSize = 12.sp
    )

    val t14 = TextStyle(
        color = Color.Black,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500
    )

    val t16 = TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600
    )

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

    val digit = TextStyle(
        color = Color.White,
        fontWeight = FontWeight.W600
    )

    val error = TextStyle(
        color = Color.Red
    )

}