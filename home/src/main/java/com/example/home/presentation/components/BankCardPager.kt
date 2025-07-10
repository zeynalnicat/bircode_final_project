package com.example.home.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.presentation.components.BankCard
import com.example.home.domain.CardModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BankCardPager(
    cards:List<CardModel>,
    onChangePage: (Int)->Unit
) {

    val pagerState = rememberPagerState(initialPage = 0, pageCount = {cards.size})

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 48.dp),
        pageSpacing = 8.dp,
        modifier = Modifier.fillMaxWidth()

    ) { page->
        onChangePage(page)
        BankCard(
            cardNumber = cards[page].cardNumber,
            cardHolder = cards[page].cardHolder, availableBalance = cards[page].availableBalance, cardColor = cards[page].cardColor.toULong()
        )
    }

    Spacer(Modifier.height(16.dp))

}