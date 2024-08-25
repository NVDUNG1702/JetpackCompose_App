package com.example.asm

import CartScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.asm.models.UserViewModel
import com.example.asm.models.cart.CartViewModel
import com.example.asm.models.notification.NotificationViewModel
import com.example.asm.models.product.ProductViewModel
import com.example.asm.navigation.BottomNavItem
import com.example.asm.navigation.BottomNavigationBar
import com.example.asm.screen.cart.HistoryCartScreen
import com.example.asm.screen.home.FavoriteScreen
import com.example.asm.screen.home.HomeScreen
import com.example.asm.screen.home.NotificationScreen
import com.example.asm.screen.home.ProfileScreen
import com.example.asm.screen.intro.IntroScreen
import com.example.asm.screen.login.SignInScreen
import com.example.asm.screen.product.DetailProductScreen
import com.example.asm.screen.signup.SignUpScreen
import com.example.asm.ui.theme.ASMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

//@Composable
//fun MyApp(){
//
//    val navController = rememberNavController();
//    val userViewModel: UserViewModel = viewModel()
//
//    val productViewModel: ProductViewModel = viewModel()
//    MaterialTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            NavHost(navController = navController, startDestination = "intro") {
//                composable("intro"){ IntroScreen(navController = navController)}
//                composable("signin"){ SignInScreen(navController = navController, userViewModel = userViewModel)}
//                composable("signup"){ SignUpScreen(navController = navController) }
//                composable("main"){ MainScreen(navController, userViewModel = userViewModel, productViewModel)}
//                composable("detailProduct"){ DetailProductScreen()}
//            }
//        }
//    }
//}
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun MainScreen(navController: NavHostController, userViewModel: UserViewModel, productViewModel: ProductViewModel) {
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(navController)
//        },
//    ) {
//        BottomNavGraph(navController = navController, userViewModel = userViewModel, productViewModel)
//    }
//}
//
//@Composable
//fun BottomNavGraph(navController: NavHostController, userViewModel: UserViewModel, productViewModel: ProductViewModel) {
//
//    NavHost(
//        navController = navController,
//        startDestination = BottomNavItem.Home.route,
//    ) {
//        composable(BottomNavItem.Home.route) { HomeScreen(navController = navController, userViewModel = userViewModel, productViewModel = productViewModel) }
//        composable(BottomNavItem.Profile.route) { ProfileScreen(navController = navController) }
//        composable(BottomNavItem.Notification.route) { NotificationScreen(navController = navController)}
//        composable(BottomNavItem.Favorite.route) { FavoriteScreen(navController = navController) }
//    }
//}

@Composable
fun MyApp() {
    val navController = rememberNavController()


    val userViewModel: UserViewModel = viewModel()


    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(navController = navController, startDestination = "intro") {
                composable("intro") { IntroScreen(navController = navController) }
                composable("signin") { SignInScreen(navController = navController, userViewModel = userViewModel) }
                composable("signup") { SignUpScreen(navController = navController) }
                composable("main") { MainScreen(userViewModel = userViewModel) }

            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(userViewModel: UserViewModel) {
    val navController = rememberNavController()
    val notificationViewModel: NotificationViewModel = viewModel()
    var showBottomBar = remember { mutableStateOf(true) }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        showBottomBar.value = destination.route == "detailProduct/{idProduct}" || destination.route == "cart" || destination.route == "historyOrder"

    }
    Scaffold(
        bottomBar = {
            if (!showBottomBar.value) {
                BottomNavigationBar(navController, notificationViewModel)
            }
        }
    ) {
        BottomNavGraph(navController = navController, userViewModel = userViewModel, notificationViewModel)
    }
}

@Composable
fun BottomNavGraph(navController: NavHostController, userViewModel: UserViewModel, notificationViewModel: NotificationViewModel) {
    val productViewModel: ProductViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
    ) {
        composable(BottomNavItem.Home.route) { HomeScreen(navController = navController, userViewModel = userViewModel, productViewModel = productViewModel) }
        composable(BottomNavItem.Profile.route) { ProfileScreen(navController = navController, userViewModel) }
        composable(BottomNavItem.Notification.route) { NotificationScreen(navController = navController, notificationViewModel) }
        composable(BottomNavItem.Favorite.route) { FavoriteScreen(navController = navController, productViewModel, cartViewModel, userViewModel) }
        composable(
            "detailProduct/{idProduct}",
            arguments = listOf(navArgument("idProduct") { type = NavType.IntType })
        ) {backStackEntry ->
            val idProduct = backStackEntry.arguments?.getInt("idProduct")
            idProduct?.let {

                DetailProductScreen(navController, productViewModel, idProduct, cartViewModel, userViewModel)
            }
        }
        composable("cart"){ CartScreen(navController, userViewModel, productViewModel, cartViewModel, notificationViewModel)}
        composable("historyOrder") {
            HistoryCartScreen(
            navController = navController,
            userViewModel = userViewModel,
            productViewModel = productViewModel,
            cartViewModel = cartViewModel
        )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ASMTheme {
    }
}
