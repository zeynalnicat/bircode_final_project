package com.example.paybill.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.R
import com.example.common.presentation.theme.Blue10
import com.example.common.presentation.theme.Gray10
import com.example.common.presentation.theme.Green10
import com.example.common.presentation.theme.Red10
import com.example.core.AppStrings

@Composable
fun BillSelector(
    type: String,
    onChange: (String)->Unit
) {
    val options = listOf(
        BillOption(AppStrings.internetBill, R.drawable.icon_internet, Green10),
        BillOption(AppStrings.electricityBill, R.drawable.icon_electricity, Red10),
        BillOption(AppStrings.waterBill,R.drawable.icon_water, Blue10),
        BillOption(AppStrings.other,R.drawable.icon_other, Gray10)
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        options.forEach { option ->
            BillOptionItem(
                option = option,
                selected = option.title == type,
                onClick = { onChange(option.title) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


data class BillOption(
    val title: String,
    val icon: Int,
    val bgColor: Color
)
