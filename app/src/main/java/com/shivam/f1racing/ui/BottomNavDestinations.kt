package com.shivam.f1racing.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import com.shivam.f1racing.R

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: Int
)

val bottomNavItems = listOf(
    BottomNavItem("home", "Home", R.drawable.home),
    BottomNavItem("dummy2", "dummy2", R.drawable.calendar_b),
    BottomNavItem("dummy3", "dummy3", R.drawable.trophy),
    BottomNavItem("dummy4", "dummy4", R.drawable.globe),
    BottomNavItem("dummy5", "dummy5", R.drawable.group)

)


