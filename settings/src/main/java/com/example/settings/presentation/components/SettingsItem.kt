package com.example.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Primary

@Composable

fun SettingsItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {

    Row(
        Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable{
                onClick()
            }
            .background(Primary.copy(alpha = 0.1f))
            .padding(16.dp)
            .fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Icon(icon, contentDescription = "", tint = Primary, modifier = Modifier.size(24.dp))
            }

            Text(title, style = DTextStyle.t14Primary)
        }

        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "", tint = Primary)
    }
}
