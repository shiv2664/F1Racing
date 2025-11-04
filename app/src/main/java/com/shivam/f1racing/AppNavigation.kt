package com.shivam.f1racing

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shivam.f1racing.ui.bottomNavItems
import com.shivam.f1racing.ui.screens.HomeScreen


@Composable
fun AppNavigation(modifier: Modifier) {

    val navController = rememberNavController()

    Scaffold(modifier=modifier) { innerPadding ->
        NavHost(
            navController=navController,
            startDestination= bottomNavItems.first().route
        ){
            composable(
                route=bottomNavItems.getOrNull(0)?.route?:""
            ){
                HomeScreen(innerPadding)
            }
        }
    }

}