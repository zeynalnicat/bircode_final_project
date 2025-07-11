package com.example.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
import com.example.common.R
import com.example.home.presentation.components.BankCardPager
import com.example.home.presentation.components.QuickAction
import com.example.home.presentation.components.RecentTransactions


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {

    val state = viewModel.state.collectAsState().value

    val scrollState = rememberScrollState()

    val snackbarHostState = remember { SnackbarHostState() }

    val quickActions = listOf(
        QuickActionModel(
            AppStrings.moneyTransfer, Green.copy(alpha = 0.4f), Green,
            R.drawable.icon_recycled_dolar
        ),
        QuickActionModel(
            AppStrings.payBill, Blue.copy(alpha = 0.2f), Blue,
            R.drawable.icon_thunder,
            onClick = {viewModel.onIntent(HomeIntent.OnNavigateToPayBill)}
        ), QuickActionModel(
            AppStrings.bankToBank, Gray.copy(alpha = 0.2f), Gray,
            R.drawable.icon_facility
        )


    )

    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
        viewModel.onIntent(HomeIntent.OnGetUserCards)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                is HomeUiEffect.OnShowError -> snackbarHostState.showSnackbar(it.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Column(
                    modifier = Modifier.clickable {
                        viewModel.onIntent(HomeIntent.OnNavigateToProfile)
                    },
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
                .padding(top = 16.dp)
                .verticalScroll(scrollState)
        ) {

            if (state.cards.isEmpty()) {

                BankCard(
                    cardHolder = "",
                    cardNumber = "****",
                    isPreview = true
                )


            } else {
                BankCardPager(
                    cards = state.cards,
                    onChangePage = { viewModel.onIntent(HomeIntent.OnSwipePager(it)) }
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
                            quickActions[index].iconColor,
                            quickActions[index].onClick
                        )

                        Spacer(Modifier.width(16.dp))


                    }

                }

                Spacer(Modifier.height(32.dp))

                Text(AppStrings.recentTransactions, style = DTextStyle.t16)

                Spacer(Modifier.height(16.dp))

                RecentTransactions(
                    state.transactions
                )


            }
        }

    }

}


data class QuickActionModel(
    val title: String,
    val containerColor: Color,
    val iconColor: Color,
    val icon: Int,
    val onClick: () -> Unit = {}

)