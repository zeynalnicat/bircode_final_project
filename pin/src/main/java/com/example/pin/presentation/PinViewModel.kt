package com.example.pin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.CoreViewModel
import com.example.core.Result
import com.example.core.ScreenModel
import com.example.pin.domain.EnterPinUseCase
import com.example.pin.domain.PinGetNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PinViewModel @Inject constructor(private val pinGetNameUseCase: PinGetNameUseCase,private val enterPinUseCase: EnterPinUseCase): ViewModel(),
    CoreViewModel<PinIntent>{

    private var navController: NavController? = null

    override fun initiateController(navController: NavController){
        this.navController = navController
    }

    private val _state = MutableStateFlow(PinState())
    val state = _state.asStateFlow()

    override fun onIntent(intent: PinIntent){
        when(intent) {
            is PinIntent.OnPressDigit -> onHandleNumberPress(intent.digit)
            PinIntent.OnClear -> _state.update { it.copy(pin = List(6){""}, currentIndex = 0, error = "") }
            PinIntent.OnRemoveDigit -> onHandleRemoveDigit()
            PinIntent.OnGetName -> onGetName()
        }
    }

    private fun onGetName(){
        viewModelScope.launch {
            when(val res = pinGetNameUseCase.invoke()){
                is Result.Error -> _state.update { it.copy(error = res.message, currentIndex = 5) }
                is Result.Success<*> -> _state.update { it.copy(name = res.data.toString()) }
            }
        }
    }

    private fun onSubmit(){
        viewModelScope.launch {
            when(val res = enterPinUseCase.invoke(_state.value.pin.joinToString())){
                is Result.Error -> _state.update { it.copy(error = res.message, currentIndex = 0, pin = List(6){""}) }
                is Result.Success<*> -> navController?.navigate(ScreenModel.Home.route){popUpTo(
                    ScreenModel.Pin.route){inclusive = true}}
            }
        }
    }

    private fun onHandleNumberPress(digit:String){

        if (_state.value.currentIndex < 6) {
            val newPin = _state.value.pin.toMutableList()
            newPin[_state.value.currentIndex] = digit
            _state.update { it.copy(pin = newPin, currentIndex = _state.value.currentIndex+1) }


        }
        if(_state.value.currentIndex == 6){
            onSubmit()
        }
    }

    private fun onHandleRemoveDigit(){
        if(_state.value.currentIndex > 0 && _state.value.pin[_state.value.currentIndex].isEmpty() ){
            val newPin = _state.value.pin.toMutableList()
            newPin[_state.value.currentIndex-1] = ""
            _state.update { it.copy(pin = newPin,currentIndex = (_state.value.currentIndex-1)) }
        }

        else if(_state.value.currentIndex>-1){
            val newPin = _state.value.pin.toMutableList()
            newPin[_state.value.currentIndex] = ""
            _state.update { it.copy(pin = newPin, currentIndex = if(_state.value.currentIndex == 0) 0 else _state.value.currentIndex-1) }
        }
    }

}