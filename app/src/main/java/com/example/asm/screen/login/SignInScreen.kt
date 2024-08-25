//
//package com.example.asm.screen.login
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.*
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.ui.text.font.FontFamily
//
//@Composable
//fun SignInScreen(navController: NavController) {
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var showPassword by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Hello!",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .fillMaxWidth(),
//            fontFamily = FontFamily.Serif,
//            color = Color(0xFF595959)
//        )
//        Text(
//            text = "WELCOME BACK",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .fillMaxWidth(),
//            fontFamily = FontFamily.Serif,
//            color = Color.Black
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Email input field
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            modifier = Modifier
//                .fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Password input field with show/hide password functionality
//        Box(modifier = Modifier.fillMaxWidth()) {
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Password") },
//                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.Transparent) // Ensure background is transparent for overlay
//            )
//            IconButton(
//                onClick = { showPassword = !showPassword },
//                modifier = Modifier
//                    .align(Alignment.CenterEnd)
//                    .padding(end = 8.dp)
//                    .size(24.dp)
//                    .background(Color.Transparent) // Ensure background is transparent for overlay
//            ) {
//                Icon(
//                    imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
//                    contentDescription = if (showPassword) "Hide password" else "Show password"
//                )
//            }
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            TextButton(onClick = { /* Handle Forgot Password */ }) {
//                Text(
//                    text = "Forgot password",
//                    color = Color(0xFF595959),
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight(weight = 400)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Login button
//        Button(
//            onClick = {  },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
//            shape = RoundedCornerShape(10.dp),
//        ) {
//            Text(text="Log in", fontSize = 18.sp)
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Sign up button
//        TextButton(
//            onClick = { navController.navigate("signup") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.CenterHorizontally)
//        ) {
//            Text(
//                text = "SIGN UP",
//                fontSize = 18.sp,
//                color = Color(0xFF595959)
//                )
//        }
//    }
//}
//
//
//package com.example.asm.screen.login
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.*
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
////import com.example.asm.network.RetrofitClient
////import com.example.asm.network.request.LoginRequest
//import com.example.asm.models.User
//import kotlinx.coroutines.launch
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import android.content.Context
//import androidx.compose.runtime.CompositionLocalProvider
//import androidx.compose.runtime.staticCompositionLocalOf
//import com.example.asm.models.LoginRequest
//import com.example.asm.utils.RetrofitInstance
//
//val LocalContext = staticCompositionLocalOf<Context> { error("No context provided") }
//
//@Composable
//fun SignInScreen(navController: NavController) {
//    val context = LocalContext.current
//
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var showPassword by remember { mutableStateOf(false) }
//    var message by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }
//
//    val scope = rememberCoroutineScope()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Hello!\nWELCOME BACK",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Email input field
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            modifier = Modifier
//                .fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Password input field with show/hide password functionality
//        Box(modifier = Modifier.fillMaxWidth()) {
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Password") },
//                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.Transparent) // Ensure background is transparent for overlay
//            )
//            IconButton(
//                onClick = { showPassword = !showPassword },
//                modifier = Modifier
//                    .align(Alignment.CenterEnd)
//                    .padding(end = 8.dp)
//                    .size(24.dp)
//                    .background(Color.Transparent) // Ensure background is transparent for overlay
//            ) {
//                Icon(
//                    imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
//                    contentDescription = if (showPassword) "Hide password" else "Show password"
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Login button
//        Button(
//            onClick = {
//                scope.launch {
//                    isLoading = true
//                    try {
//                        val response = RetrofitInstance.api.login(
//                            LoginRequest(email, password)
//                        )
//                        if (response.status == 200) {
//                            message = "Login successful!"
//                            // Lưu token và điều hướng đến màn hình khác
//                            saveTokens(context, response.ACCESS_TOKEN.token, response.REFRESH_TOKEN.token)
//                            navController.navigate("home")
//                        } else {
//                            message = "Login failed: ${response.status}"
//                        }
//                    } catch (e: Exception) {
//                        message = "Error: ${e.message}"
//                    } finally {
//                        isLoading = false
//                    }
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF595959)),
//            shape = RoundedCornerShape(10.dp),
//        ) {
//            Text("Log in", color = Color.White)
//        }
//
//        if (isLoading) {
//            Spacer(modifier = Modifier.height(16.dp))
//            CircularProgressIndicator()
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Sign up button
//        TextButton(
//            onClick = { /* Navigate to sign up screen */ },
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.CenterHorizontally)
//        ) {
//            Text("Don't have an account? Sign up", color = Color(0xFF595959))
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Display message
//        Text(
//            text = message,
//            color = if (message.startsWith("Error")) Color.Red else Color.Green,
//            fontSize = 16.sp
//        )
//    }
//}
//
//fun saveTokens(context: Context, accessToken: String, refreshToken: String) {
//    val sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
//    with(sharedPref.edit()) {
//        putString("access_token", accessToken)
//        putString("refresh_token", refreshToken)
//        apply()
//    }
//}


package com.example.asm.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.asm.models.LoginRequest
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.asm.models.UserViewModel
import com.example.asm.utils.RetrofitInstanceLogin

@Composable
fun SignInScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
//    val userViewModel: UserViewModel = viewModel()

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier
                .fillMaxSize()
                ,
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {


            Text(
                text = "Hello!\nWELCOME BACK",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                )
                IconButton(
                    onClick = { showPassword = !showPassword },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                        .size(24.dp)
                        .background(Color.Transparent)
                ) {
                    Icon(
                        imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (showPassword) "Hide password" else "Show password"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        try {
                            val response = RetrofitInstanceLogin.api.login(
                                LoginRequest(email, password)
                            )
                            if (response.status == 200) {
//                                message = "Login successful: ${response}"
                                saveTokens(
                                    context,
                                    response.ACCESS_TOKEN.token,
                                    response.REFRESH_TOKEN.token
                                )
                                    userViewModel.setUser(response.user)
                                    navController.navigate("main")

                            } else {
                                message = "Login failed: ${response.status}"
                            }

                        } catch (e: Exception) {

                            message = "Error: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF595959)),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text("Log in", color = Color.White)
            }



            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { navController.navigate("signup") },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Don't have an account? Sign up", color = Color(0xFF595959))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = message,
                color = if (message.startsWith("Error")) Color.Red else Color.Green,
                fontSize = 16.sp
            )
        }
    }
}

fun saveTokens(context: Context, accessToken: String, refreshToken: String) {
    val sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("access_token", accessToken)
        putString("refresh_token", refreshToken)
        apply()
    }
}
//dungsenpai01101975@gmail.com