package com.example.common.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.common.theme.Primary



@Composable
fun DTextField(
    value: String , onChange: (String)->Unit,placeHolder:String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None){

    OutlinedTextField(
        value = value,
        placeholder = { Text(placeHolder) } ,
        onValueChange = onChange,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = Color.Black,
              unfocusedTextColor = Color.Black,
              unfocusedPlaceholderColor = Color.Gray,
              focusedPlaceholderColor = Color.DarkGray,
              unfocusedBorderColor = Color.White,
              focusedBorderColor = Primary,
        )


    )
}