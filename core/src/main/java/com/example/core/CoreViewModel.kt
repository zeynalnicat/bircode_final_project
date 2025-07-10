package com.example.core

import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow

interface CoreViewModel<T> {

    fun initiateController(navController: NavController)

    fun onIntent(intent: T)
}