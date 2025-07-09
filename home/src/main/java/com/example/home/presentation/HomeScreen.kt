package com.example.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.common.presentation.components.BankCard
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Primary
import com.example.common.presentation.theme.Secondary
import com.example.core.AppStrings
import com.example.home.R
import com.example.home.domain.CardModel
import com.example.home.presentation.components.BankCardPager


@Composable
fun HomeScreen(navController: NavController,viewModel: HomeViewModel){

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
    }

    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val avatarModifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .border(0.5.dp, Color.Transparent, CircleShape)

                    if (state.imgUri.isEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.default_avatar),
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = avatarModifier
                        )
                    } else {
                        AsyncImage(
                            model = state.imgUri,
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = avatarModifier
                        )
                    }
                }


                Text(AppStrings.homeTitle, style = DTextStyle.title.copy(color = Primary))

                Icon(
                    Icons.Default.Add, contentDescription = "", modifier = Modifier.size(32.dp), tint = Secondary
                )

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(vertical = 32.dp)
        ) {
            BankCardPager(
                listOf(CardModel("Nijat Zeynalli",Secondary.value.toString(), availableBalance = 300, cardNumber = "1231313",),CardModel("Nijat Zeynalli",Primary.value.toString(), availableBalance = 1000, cardNumber = "555343",))
            )
        }
    }

}
