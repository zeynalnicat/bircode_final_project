package com.example.home.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.core.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class HomeViewModel: ViewModel() {

    private val _state = MutableStateFlow(HomeState())

    val state = _state.asStateFlow()

    private var navController: NavController? = null

    fun initiateController(navController: NavController){
        this.navController = navController
    }

    fun onIntent(intent: HomeIntent){
        when(intent){
            HomeIntent.OnNavigateToAddCard -> navController?.navigate(ScreenModel.NewCard.route)
        }
    }

}