package com.shivam.f1racing.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("home", "Home", Icons.Filled.Home),
    BottomNavItem("search", "ViewPager", Icons.Filled.Warning),
    BottomNavItem("favorites", "Favorites", Icons.Filled.Favorite),
    BottomNavItem("profile", "Profile", Icons.Filled.Person)
)


