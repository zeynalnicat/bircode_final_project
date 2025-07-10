package com.example.newcard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.core.CoreViewModel
import com.example.core.Result
import com.example.newcard.domain.CreateNewCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewCardViewModel @Inject constructor(private val createNewCardUseCase: CreateNewCardUseCase): ViewModel(), CoreViewModel<NewCardIntent>{

    private val _state = MutableStateFlow(NewCardState())

    private val _effect = MutableSharedFlow<NewCardUiEffect>()

    val state = _state.asStateFlow()

    val effect = _effect.asSharedFlow()

    private var navController: NavController? = null

    override fun initiateController(navController: NavController) {
        this.navController = navController
    }

    override fun onIntent(intent: NewCardIntent){
        when(intent){
            is NewCardIntent.OnChangeColor -> _state.update { it.copy (color = intent.color)}
            is NewCardIntent.OnChangeDeposit -> _state.update { it.copy(initialBalance = intent.deposit) }
            is NewCardIntent.OnChangeName -> _state.update { it.copy(name = intent.name) }
            NewCardIntent.OnHandleSubmit -> createNewCard()
            NewCardIntent.OnNavigateBack -> navController?.popBackStack()
        }
    }

    private fun createNewCard(){
        _state.update { it.copy(loading = true) }
        val cardNumber = (1000000000000000..9999999999999999).random()
        viewModelScope.launch {
            when(val res = createNewCardUseCase.invoke(_state.value.name,_state.value.color,_state.value.initialBalance,cardNumber.toString())) {
                is Result.Error -> {
                    _effect.emit(NewCardUiEffect.OnShowError(res.message))
                    _state.update { it.copy(loading = false) }
                 }
                is Result.Success<*> -> navController?.popBackStack()
            }
        }
    }


}