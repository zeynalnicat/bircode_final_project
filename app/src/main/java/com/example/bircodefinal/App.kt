package com.example.bircodefinal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bircodefinal.navigation.AppNavigator
import com.example.bircodefinal.navigation.TopLevelRoute
import com.example.core.ScreenModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun App(innerPaddingValues: PaddingValues,firebaseAuth: FirebaseAuth){
    val navController = rememberNavController()

    val topLevelRoutes = listOf(
        TopLevelRoute(ScreenModel.Home.route,"Home", ImageVector.vectorResource(R.drawable.icon_home)),
        TopLevelRoute(ScreenModel.Settings.route,"Settings", Icons.Default.Settings)
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            val hiddenRoutes = listOf(
                ScreenModel.SignUp.route,
                ScreenModel.Login.route,
                ScreenModel.Pin.route,
                ScreenModel.NewCard.route,
                ScreenModel.Profile.route
            )

            if (currentRoute !in hiddenRoutes) {
                Surface(
                    tonalElevation = 4.dp,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 8.dp
                ) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = 0.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        topLevelRoutes.forEach { route ->
                            val selected = currentRoute == route.route

                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    if (!selected) {
                                        navController.navigate(route.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = route.icon,
                                        contentDescription = route.name,
                                        tint = if (selected)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                label = {
                                    Text(
                                        text = route.name,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selected)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                alwaysShowLabel = true,
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent,
                                )
                            )
                        }
                    }
                }
            }
             }
    ) { innerPadding->
        AppNavigator(innerPadding,navController,firebaseAuth)
    }


}