package com.example.settings.presentation

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.common.presentation.components.DTopBar
import com.example.core.AppStrings
import com.example.settings.presentation.components.SettingsItem


@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel) {

    val settingsItems = listOf(
        SettingsUiModel(
            AppStrings.profile,
            Icons.Default.Person,
            { viewModel.onIntent(SettingsIntent.OnNavigateToProfile) }),
        SettingsUiModel(AppStrings.logOut, Icons.Default.ExitToApp, {
            viewModel.onIntent(
                SettingsIntent.OnLogOut
            )
        })
    )

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                is SettingsUiEffect.OnShowError -> snackBarHostState.showSnackbar(it.message)
            }

        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            DTopBar(AppStrings.settings, isTitle = true)
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            settingsItems.forEach {
                SettingsItem(it.title, it.icon) { it.onClick() }
            }
        }
    }


}


data class SettingsUiModel(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)