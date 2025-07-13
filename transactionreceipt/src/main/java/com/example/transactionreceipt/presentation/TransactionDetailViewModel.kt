package com.example.transactionreceipt.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.common.domain.TransactionModel
import com.example.core.CoreViewModel
import com.example.core.Result
import com.example.transactionreceipt.domain.GetTransactionDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransactionDetailViewModel @Inject constructor(private val getTransactionDetailsUseCase: GetTransactionDetailsUseCase) :
    ViewModel(), CoreViewModel<TransactionDetailIntent> {


    private val _state = MutableStateFlow(TransactionDetailState())

    private val _effect = MutableSharedFlow<TransactionDetailUiEffect>()

    val state = _state.asStateFlow()

    val effect = _effect.asSharedFlow()

    private var navController: NavController? = null

    override fun initiateController(navController: NavController) {
        this.navController = navController
    }

    override fun onIntent(intent: TransactionDetailIntent) {
        when (intent) {
            TransactionDetailIntent.OnNavigateBack -> navController?.popBackStack()
            is TransactionDetailIntent.OnSetTransactionId -> _state.update { it.copy(id = intent.id) }
            TransactionDetailIntent.OnFetchTransactionDetails -> onFetchTransactionDetails()
        }
    }

    private fun onFetchTransactionDetails() {
        viewModelScope.launch {
            when (val res = getTransactionDetailsUseCase(_state.value.id)) {
                is Result.Error -> _effect.emit(TransactionDetailUiEffect.OnShowError(res.message))
                is Result.Success<TransactionModel> -> _state.update {
                    it.copy(
                        amount = res.data.amount,
                        transactionName = res.data.transactionName,
                        name = res.data.name,
                        isExpense = res.data.isExpense,
                        date = res.data.date,
                        id = res.data.id
                    )
                }
            }
        }
    }
}