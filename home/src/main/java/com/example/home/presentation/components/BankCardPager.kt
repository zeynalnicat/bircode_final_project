package com.example.home.presentation.components

import android.gesture.Gesture
import android.view.GestureDetector
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.common.presentation.components.BankCard
import com.example.common.domain.CardModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BankCardPager(
    cards: List<CardModel>,
    onChangePage: (Int) -> Unit,
    onClick: (String) -> Unit,
) {

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { cards.size })

    LaunchedEffect(pagerState.currentPage) {
        onChangePage(pagerState.currentPage)
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 48.dp),
        pageSpacing = 8.dp,
        modifier = Modifier.fillMaxWidth()

    ) { page ->
        val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
        val clampedOffset = pageOffset.coerceIn(-1f, 1f)


        val rotation = clampedOffset * 30f

        val scale = 1f - (0.15f * kotlin.math.abs(clampedOffset))

        Box(
            modifier = Modifier.graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationY = rotation
            )
        ){
            BankCard(
                cardNumber = cards[page].cardNumber,
                cardHolder = cards[page].cardHolder,
                availableBalance = cards[page].availableBalance,
                cardColor = cards[page].cardColor.toULong(),
                modifier = Modifier.clickable { onClick(cards[page].cardId) }
            )
        }

    }

    Spacer(Modifier.height(16.dp))

}