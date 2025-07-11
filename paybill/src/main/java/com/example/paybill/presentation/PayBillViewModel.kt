package com.example.paybill.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.core.CoreViewModel
import com.example.core.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PayBillViewModel : ViewModel(), CoreViewModel<PayBillIntent> {

    private val _state = MutableStateFlow(PayBillState())

    val state = _state.asStateFlow()

    private var navController: NavController? = null

    override fun initiateController(navController: NavController) {
        this.navController = navController
    }

    override fun onIntent(intent: PayBillIntent) {
        when (intent) {
            is PayBillIntent.OnChangeAmount -> _state.update { it.copy(amount = intent.amount) }
            is PayBillIntent.OnChangeBillType -> _state.update { it.copy(billType = intent.type) }
            PayBillIntent.OnNavigateBack -> navController?.popBackStack()
            PayBillIntent.OnNavigateToPayOperation -> navController?.navigate("pay-operation/${_state.value.billType}")
        }
    }
}