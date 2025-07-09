package com.example.home.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
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

    }

}