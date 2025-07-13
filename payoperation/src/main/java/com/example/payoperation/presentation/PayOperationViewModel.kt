package com.example.payoperation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.common.domain.CardModel
import com.example.core.AppErrors
import com.example.core.CoreViewModel
import com.example.core.Result
import com.example.core.ScreenModel
import com.example.payoperation.domain.PayOperationGetCardsUseCase
import com.example.payoperation.domain.PayOperationOnTopUpUseCase
import com.example.payoperation.domain.PayOperationSaveTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PayOperationViewModel @Inject constructor(
    private val payOperationGetCardsUseCase: PayOperationGetCardsUseCase,
    private val payOperationSaveTransactionUseCase: PayOperationSaveTransactionUseCase,
    private val payOperationOnTopUpUseCase: PayOperationOnTopUpUseCase
) : ViewModel(), CoreViewModel<PayOperationIntent> {

    private var navController: NavController? = null

    private val _state = MutableStateFlow(PayOperationState())

    private val _effect = MutableSharedFlow<PayOperationUiEffect>()

    val effect = _effect.asSharedFlow()

    val state = _state.asStateFlow()

    override fun initiateController(navController: NavController) {
        this.navController = navController
    }

    override fun onIntent(intent: PayOperationIntent) {
        when (intent) {
            PayOperationIntent.OnNavigateBack -> navController?.popBackStack()
            is PayOperationIntent.OnSetTransactionType -> _state.update { it.copy(transactionType = intent.transactionType) }
            is PayOperationIntent.OnSetCard -> _state.update { it.copy(selectedCardId = intent.card) }
            PayOperationIntent.OnGetCards -> getCards()
            PayOperationIntent.OnNavigateToNewCard -> navController?.navigate(ScreenModel.NewCard.route) {
                popUpTo(
                    ScreenModel.PayOperation
                ) { inclusive = true }
            }

            is PayOperationIntent.OnSetAmount -> _state.update { it.copy(amount = intent.amount) }
            PayOperationIntent.OnHandleSubmit -> onHandleSubmit()
            is PayOperationIntent.OnSetIsTopUp -> _state.update { it.copy(isTopUp = intent.isTopUp) }
            PayOperationIntent.OnHandleTransfer -> onHandleTransfer()
            is PayOperationIntent.OnSetReceiverCardNumber -> _state.update {
                it.copy(
                    receiverCardNumber = intent.number
                )
            }
        }
    }

    private fun onHandleTransfer() {

    }

    private fun onHandleSubmit() {
        _state.update { it.copy(error = "", isLoading = true) }
        val selectedCard = _state.value.cards.find { it.cardId == _state.value.selectedCardId }

        if (_state.value.isTopUp) {
            viewModelScope.launch {
                when (val res = payOperationOnTopUpUseCase.invoke(
                    _state.value.selectedCardId,
                    _state.value.amount,
                    _state.value.transactionType
                )) {
                    is Result.Error -> _effect.emit(PayOperationUiEffect.OnShowError(res.message))
                    is Result.Success<*> -> {
                        navController?.navigate(ScreenModel.Home.route) {
                            popUpTo(ScreenModel.PayOperation.route) {

                            }
                            popUpTo(ScreenModel.PayBill.route) { inclusive = true }
                        }
                        _state.update { it.copy(error = "", isLoading = false) }
                    }
                }
            }


        } else {
            if (selectedCard != null && selectedCard.availableBalance.toInt() < _state.value.amount.toInt()) {
                _state.update { it.copy(error = AppErrors.notEnoughAmount, isLoading = false) }
            } else {

                viewModelScope.launch {
                    when (val res = payOperationSaveTransactionUseCase.invoke(
                        _state.value.selectedCardId,
                        _state.value.amount,
                        _state.value.transactionType
                    )) {
                        is Result.Error -> _effect.emit(PayOperationUiEffect.OnShowError(res.message))
                        is Result.Success<*> -> {
                            navController?.navigate(ScreenModel.Home.route) {
                                popUpTo(ScreenModel.PayOperation.route) {

                                }
                                popUpTo(ScreenModel.PayBill.route) { inclusive = true }
                            }
                            _state.update { it.copy(error = "", isLoading = false) }
                        }
                    }
                }


            }
        }

    }

    private fun getCards() {
        viewModelScope.launch {
            when (val res = payOperationGetCardsUseCase()) {
                is Result.Error -> _effect.emit(PayOperationUiEffect.OnShowError(res.message))
                is Result.Success<List<CardModel>> -> _state.update {
                    it.copy(
                        cards = res.data,
                        selectedCardId = res.data[0].cardId
                    )
                }
            }
        }
    }
}