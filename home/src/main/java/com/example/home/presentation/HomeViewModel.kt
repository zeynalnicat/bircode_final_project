package com.example.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.CoreViewModel
import com.example.core.Result
import com.example.core.ScreenModel
import com.example.common.domain.CardModel
import com.example.common.domain.TransactionModel
import com.example.core.AppStrings
import com.example.home.domain.GetUserCardsUseCase
import com.example.home.domain.HomeGetCardTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserCardsUseCase: GetUserCardsUseCase,
    private val homeGetCardTransactionsUseCase: HomeGetCardTransactionsUseCase
) : ViewModel(),
    CoreViewModel<HomeIntent> {

    private val _state = MutableStateFlow(HomeState())

    private val _effect = MutableSharedFlow<HomeUiEffect>()

    val state = _state.asStateFlow()

    val effect = _effect.asSharedFlow()

    private var navController: NavController? = null

    override fun initiateController(navController: NavController) {
        this.navController = navController
    }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.OnNavigateToAddCard -> navController?.navigate(ScreenModel.NewCard.route)
            HomeIntent.OnGetUserCards -> onHandleUserCards()
            HomeIntent.OnNavigateToProfile -> navController?.navigate(ScreenModel.Profile.route)
            is HomeIntent.OnSwipePager -> _state.update { it.copy(currentCardIndex = intent.p1) }
            HomeIntent.OnNavigateToPayBill -> navController?.navigate(ScreenModel.PayBill.route)
            is HomeIntent.OnGetCardTransactions -> getCardTransactions()
            is HomeIntent.OnNavigateToCardDetails -> navController?.navigate(
                ScreenModel.CardDetails.withId(
                    intent.id
                )
            )

            HomeIntent.OnNavigateToPayOperation -> navController?.navigate(
                ScreenModel.PayOperation.withTransactionType(AppStrings.topUp, true, false)
            )

            is HomeIntent.OnNavigateToTransactionDetails -> navController?.navigate(
                ScreenModel.TransactionDetails.withId(
                    intent.id
                )
            )

            HomeIntent.OnNavigateToBankToBank -> navController?.navigate(
                ScreenModel.PayOperation.withTransactionType(
                    AppStrings.bankToBank, false, true
                )
            )
        }
    }

    private fun getCardTransactions() {
        if (_state.value.cards.isNotEmpty()) {
            viewModelScope.launch {
                when (val res =
                    homeGetCardTransactionsUseCase(_state.value.cards[_state.value.currentCardIndex].cardId)) {
                    is Result.Error -> _effect.emit(HomeUiEffect.OnShowError(res.message))
                    is Result.Success<List<TransactionModel>> -> {
                        _state.update {
                            it.copy(transactions = res.data)
                        }
                    }
                }
            }
        }

    }

    private fun onHandleUserCards() {

        viewModelScope.launch {
            when (val res = getUserCardsUseCase.invoke()) {
                is Result.Error -> _effect.emit(HomeUiEffect.OnShowError(res.message))
                is Result.Success<List<CardModel>> -> {
                    _state.update { it.copy(cards = res.data) }
                    getCardTransactions()
                }
            }
        }
    }


}