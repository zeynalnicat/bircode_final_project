package com.example.bircodefinal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bircodefinal.navigation.AppNavigator
import com.example.bircodefinal.navigation.ScreenModel
import com.example.bircodefinal.navigation.TopLevelRoute


@Composable
fun App(innerPaddingValues: PaddingValues){
    val navController = rememberNavController()

    val topLevelRoutes = listOf(
        TopLevelRoute(ScreenModel.Home.route,"Home", Icons.Default.Home),
        TopLevelRoute(ScreenModel.Profile.route,"Profile", Icons.Default.Person),
        TopLevelRoute(ScreenModel.Settings.route,"Settings", Icons.Default.Settings)
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if(currentRoute !in listOf(ScreenModel.SignUp.route, ScreenModel.Login.route)){
                NavigationBar {
                          topLevelRoutes.forEach { route->
                              val selected = currentRoute == route.route
                              NavigationBarItem(
                                  selected = selected,
                                  icon = {Icon(route.icon, contentDescription = route.name)},
                                  label = { Text(route.name) },
                                  onClick = {
                                      if(!selected){
                                          navController.navigate(route.route){
                                              popUpTo(navController.graph.startDestinationId) {
                                                  saveState = true
                                              }
                                              launchSingleTop = true
                                              restoreState = true
                                          }
                                      }
                                  }
                              )
                          }
                }
        }

        }
    ) { innerPadding->
        AppNavigator(innerPadding,navController)
    }


}