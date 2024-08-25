package com.example.asm.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem (val title: String, val route: String, val icon: ImageVector, val iconOutline: ImageVector){
    object Home : BottomNavItem("Home", "home", Icons.Filled.Home, Icons.Outlined.Home)
    object Favorite : BottomNavItem("Favorite", "favorite", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)
    object Notification : BottomNavItem("Notification", "notification", Icons.Filled.Notifications, Icons.Outlined.Notifications)
    object Profile : BottomNavItem("Profile", "profile", Icons.Filled.Person, Icons.Outlined.Person)
}
