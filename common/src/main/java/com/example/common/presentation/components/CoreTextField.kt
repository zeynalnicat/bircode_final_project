package com.example.common.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.common.presentation.theme.Gray
import com.example.common.presentation.theme.TextFieldGray


@Composable
fun CoreTextField(
    value: String,
    onChange: (String)->Unit,
    placeHolder:String,
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,

) {

    Column {
        TextField(
            value = value,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {onChange(it)},
            isError = isError,
            enabled = enabled,
            placeholder = { Text(placeHolder) },
            keyboardOptions = keyboardOptions,
            keyboardActions =  keyboardActions,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = TextFieldGray.copy(alpha = 0.2f),
                focusedContainerColor = TextFieldGray.copy(alpha = 0.2f),
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                disabledIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = Gray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = Color.DarkGray,
            )

        )
        Spacer(Modifier.height(4.dp))

        if (supportingText != null && (isError || supportingText.isNotEmpty())) {
            Text(
                text = supportingText,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}