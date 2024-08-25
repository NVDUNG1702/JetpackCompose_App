package com.example.asm.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.asm.models.notification.NotificationViewModel

@Composable
fun BottomNavigationBar(navController: NavHostController, notificationViewModel: NotificationViewModel) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorite,
        BottomNavItem.Notification,
        BottomNavItem.Profile,

    )


    val notifis = notificationViewModel.notification.observeAsState().value?.find { it.status == false }
    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(56.dp) // Optional: Add padding around the NavigationBar
            .offset(y = (-20).dp) // Move the NavigationBar up to create the floating effect
            .background(Color.Transparent)

        ,
        contentAlignment = Alignment.Center,
    )
    {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .height(100.dp)
                .shadow(10.dp, RoundedCornerShape(20.dp)) // Add shadow for the floating effect
                .clip(RoundedCornerShape(20.dp)) // Round corners with 20dp radius

        ) {
            val currentRoute = currentRoute(navController = navController)
            items.forEach { item ->
                NavigationBarItem(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically),
                    icon = {
                        Box(modifier = Modifier){
                            Icon(
                                imageVector = if (currentRoute == item.route) item.icon else item.iconOutline,
                                contentDescription = item.title,
                                modifier = Modifier
                                    .size(40.dp)
//                                    .align(Alignment.CenterVertically)
                                    .fillMaxHeight()
                            )
                            if(item.route == "notification" && notifis != null){
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(Color.Red, RoundedCornerShape(20.dp))
                                        .align(Alignment.TopEnd)
                                        .offset(x = 8.dp, y = (-8).dp)
                                )
                            }
                        }

                    },

                    selected = currentRoute == item.route,
                    alwaysShowLabel = false,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },

                    )

            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    return navBackStackEntry.value?.destination?.route
}