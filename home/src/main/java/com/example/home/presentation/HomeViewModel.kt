package com.example.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.CoreViewModel
import com.example.core.Result
import com.example.core.ScreenModel
import com.example.home.domain.CardModel
import com.example.home.domain.GetUserCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val getUserCardsUseCase: GetUserCardsUseCase): ViewModel(),
    CoreViewModel<HomeIntent> {

    private val _state = MutableStateFlow(HomeState())

    val state = _state.asStateFlow()

    private var navController: NavController? = null

    override fun initiateController(navController: NavController){
        this.navController = navController
    }

    override fun onIntent(intent: HomeIntent){
        when(intent){
            HomeIntent.OnNavigateToAddCard -> navController?.navigate(ScreenModel.NewCard.route)
            HomeIntent.OnGetUserCards -> onHandleUserCards()
            HomeIntent.OnNavigateToProfile -> navController?.navigate(ScreenModel.Profile.route)
        }
    }

    private fun onHandleUserCards(){

        viewModelScope.launch {
            when(val res = getUserCardsUseCase.invoke()){
                is Result.Error -> TODO()
                is Result.Success<List<CardModel>> -> _state.update { it.copy(cards = res.data) }
            }
        }
    }



}