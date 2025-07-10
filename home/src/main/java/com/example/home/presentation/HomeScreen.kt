package com.example.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.common.presentation.components.BankCard
import com.example.common.presentation.theme.Blue
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Gray
import com.example.common.presentation.theme.Green
import com.example.common.presentation.theme.Primary
import com.example.common.presentation.theme.Secondary
import com.example.core.AppStrings
import com.example.home.R
import com.example.home.domain.CardModel
import com.example.home.presentation.components.BankCardPager
import com.example.home.presentation.components.QuickAction


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {

    val state = viewModel.state.collectAsState().value

    val quickActions = listOf(
        QuickActionModel(
            AppStrings.moneyTransfer, Green.copy(alpha = 0.4f), Green,
            com.example.common.R.drawable.icon_recycled_dolar
        ),
        QuickActionModel(
            AppStrings.payBill, Blue.copy(alpha = 0.2f), Blue,
            com.example.common.R.drawable.icon_thunder
        ), QuickActionModel(
            AppStrings.bankToBank, Gray.copy(alpha = 0.2f), Gray,
            com.example.common.R.drawable.icon_facility
        )


    )

    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
        viewModel.onIntent(HomeIntent.OnGetUserCards)
    }

    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val avatarModifier = Modifier
                        .size(32.dp)
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
                    Icons.Default.Add,
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            viewModel.onIntent(HomeIntent.OnNavigateToAddCard)
                        },
                    tint = Secondary,
                )

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 24.dp)
        ) {

            if (state.cards.isEmpty()) {

                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp).clickable{
                            viewModel.onIntent(HomeIntent.OnNavigateToAddCard)
                        },
                    contentAlignment = Alignment.Center

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                alpha = 0.9f
                                shadowElevation = 8.dp.toPx()
                                shape = RoundedCornerShape(20.dp)
                                clip = true
                            }
                            .blur(16.dp)
                    ) {
                        BankCard(
                            cardColor = Secondary.value,
                            cardHolder = "",
                            availableBalance = ""
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp).clickable{
                            viewModel.onIntent(HomeIntent.OnNavigateToAddCard)}
                    )
                }



            } else {
                BankCardPager(
                    cards = state.cards
                )
            }


            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(AppStrings.quickActions, style = DTextStyle.t16)
                Spacer(Modifier.height(16.dp))

                LazyRow {
                    items(quickActions.size) { index ->

                        QuickAction(
                            quickActions[index].title,
                            quickActions[index].icon,
                            quickActions[index].containerColor,
                            quickActions[index].iconColor
                        )

                        Spacer(Modifier.width(16.dp))


                    }

                }

                Spacer(Modifier.height(32.dp))

                Text(AppStrings.recentTransactions, style = DTextStyle.t16)


            }
        }

    }

}


data class QuickActionModel(
    val title: String,
    val containerColor: Color,
    val iconColor: Color,
    val icon: Int,

    )