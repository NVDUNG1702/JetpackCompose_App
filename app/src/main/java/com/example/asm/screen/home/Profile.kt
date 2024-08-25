package com.example.asm.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.asm.models.UserViewModel

@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel){

    val user = userViewModel.user.observeAsState().value
    val avatar = user?.avatar;

    val image = replaceLocalhostWithIp(avatar.toString(), "https://6b97-14-185-65-122.ngrok-free.app/");

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

        ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile picture
        Spacer(modifier = Modifier.height(50.dp))
//        Row (
//            modifier = Modifier
//                .size(150.dp)
////                .clip(CircleShape)
//                .padding(16.dp)
//
//            ,
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
            Image(
                painter = rememberImagePainter(image),
                contentDescription = null,
                modifier = Modifier
//                    .clip(shape = MaterialTheme.shapes.extraSmall)
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        Color.Black,
                        shape = CircleShape
                    )
                ,
            contentScale = ContentScale.Crop
            )
//        }
        // User name and email
        Text(
            text = ""+user?.fullName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = ""+user?.email,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        // Menu options
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
            ,
            contentPadding = PaddingValues(16.dp)
        ) {
            items(menuOptions) { option ->
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()

                    ,
                    onClick = {if (option.title == "My orders") { navController.navigate("historyOrder")}},
                    elevation = CardDefaults.cardElevation(
                        4.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    border = BorderStroke(2.dp, Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = option.title, fontWeight = FontWeight.Bold)
                        Text(text = option.subtitle, fontSize = 14.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

data class MenuOption(val title: String, val subtitle: String)

val menuOptions = listOf(
    MenuOption("My orders", "Already have 10 orders"),
    MenuOption("Shipping Addresses", "03 Addresses"),
    MenuOption("Payment Method", "You have 2 cards"),
    MenuOption("My reviews", "Reviews for 5 items"),
    MenuOption("Settings", "Notification, Password, FAQ, Contact")
)
