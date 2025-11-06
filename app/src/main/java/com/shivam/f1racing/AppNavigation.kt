package com.shivam.f1racing

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shivam.f1racing.ui.bottomNavItems
import com.shivam.f1racing.ui.data.Schedule
import com.shivam.f1racing.ui.screens.Dummy2
import com.shivam.f1racing.ui.screens.Dummy3
import com.shivam.f1racing.ui.screens.Dummy4
import com.shivam.f1racing.ui.screens.Dummy5
import com.shivam.f1racing.ui.screens.detail.DetailScreen
import com.shivam.f1racing.ui.screens.home.HomeScreen


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@Preview
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.padding(),
        bottomBar = {
            NavigationBar(containerColor = Color.Black) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.icon),                // use Icon composable
                                contentDescription = screen.label,
                                modifier = Modifier.size(24.dp),
                                tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true)
                                    Color.White else Color.Gray
                            )
                        },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        ), onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = bottomNavItems.first().route
        ) {
            composable(
                route = bottomNavItems.getOrNull(0)?.route ?: ""
            ) {
                HomeScreen(
                    innerPadding,
                    { schedule ->
                        val json = schedule.toJson()
                        val encoded = Uri.encode(json)
                        navController.navigate("item_detail/$encoded")
                    })
            }

            composable(route = "item_detail/{scheduleJson}",
                arguments = listOf(
                    navArgument("scheduleJson") { type = NavType.StringType }
                )) {  backStackEntry->
                val json = backStackEntry.arguments?.getString("scheduleJson") ?: ""
                val schedule = json.decode<Schedule>()

                DetailScreen(innerPadding,schedule)
            }

            composable(route = bottomNavItems.getOrNull(1)?.route ?: "") {
                Dummy2()
            }

            composable(route = bottomNavItems.getOrNull(2)?.route ?: "") {
                Dummy3()
            }

            composable(route = bottomNavItems.getOrNull(3)?.route ?: "") {
                Dummy4()
            }

            composable(route = bottomNavItems.getOrNull(4)?.route ?: "") {
                Dummy5()
            }


        }
    }

}