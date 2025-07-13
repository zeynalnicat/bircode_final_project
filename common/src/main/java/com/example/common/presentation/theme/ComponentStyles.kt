package com.example.common.presentation.theme

import android.R
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color


object DTextStyle {


    val t12 = TextStyle(
        color = Color.White,
        fontSize = 12.sp
    )

    val t12Gray = t12.copy(
        color = Gray,
        fontSize = 12.sp
    )

    val t12Bold = TextStyle(
        color = Color.Black,
        fontSize = 12.sp,
        fontWeight = FontWeight.W600
    )

    val t14 = TextStyle(
        color = Color.Black,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500
    )

    val t14Gray = t14.copy(color = Gray)

    val t14Primary = t14.copy(color= Primary)



    val t16 = TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600
    )

    val t16Primary = t16.copy(color = Primary)

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