package com.example.carddetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.carddetails.domain.CardDetailsGetCardUseCase
import com.example.common.domain.CardModel
import com.example.core.CoreViewModel
import com.example.core.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CardDetailViewModel @Inject constructor(private val cardUseCase: CardDetailsGetCardUseCase ): ViewModel(), CoreViewModel<CardDetailIntent> {

    private val _state = MutableStateFlow(CardDetailsState())

    private val _effect = MutableSharedFlow<CardDetailUiEffect>()
    val state = _state.asStateFlow()

    val effect = _effect.asSharedFlow()

    private var navController: NavController? = null
    override fun initiateController(navController: NavController) {
        this.navController = navController
    }

    override fun onIntent(intent: CardDetailIntent) {
        when(intent){
            CardDetailIntent.OnNavigateBack -> navController?.popBackStack()
            is CardDetailIntent.OnGetCardDetails -> onGetCardDetails(intent.cardId)
        }
    }

    private fun onGetCardDetails(cardId:String){
        viewModelScope.launch {
            when(val res = cardUseCase(cardId)){
                is Result.Error -> _effect.emit(CardDetailUiEffect.OnShowError(res.message))
                is Result.Success<CardModel> -> _state.update { it.copy(card = res.data) }
            }
        }
    }
}