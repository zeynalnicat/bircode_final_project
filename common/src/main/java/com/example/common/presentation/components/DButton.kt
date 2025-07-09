package com.example.common.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.common.presentation.theme.Secondary


@Composable
fun DButton(title:String,onClick:()->Unit,
            elevation: ButtonElevation? = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 8.dp,
                disabledElevation = 0.dp
            ),
            loading: Boolean = false,) {

    Button(
        modifier = Modifier.fillMaxWidth(),
        elevation = elevation,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Secondary
        ),
    ) {
        if(!loading) Text(title) else CircularProgressIndicator(color = Color.White)

    }
}