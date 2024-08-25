package com.example.asm.screen.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun IntroScreen(navController: NavController) {
//    val gelasio = FontFamily(Font(R.font.gelasio)) // Thay thế R.font.gelasio bằng resource của bạn
//    val nunitoSans = FontFamily(Font(R.font.nunito_sans)) // Thay thế R.font.nunito_sans bằng resource của bạn

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.example.asm.R.drawable.background_intro),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.Start,
//            modifier = Modifier.padding(start = 30.dp)
        ) {
            Text(
                text = "MAKE YOUR",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                lineHeight = 30.47.sp,
                letterSpacing = 0.05.em,
                color = Color(0xFF595959),
                modifier = Modifier
                    .width(354.dp)
//                    .height(30.dp)
//                    .padding(top = 231.dp)
            )
            Spacer(modifier = Modifier
                .padding(10.dp)
            )

            Text(
                text = "HOME BEAUTIFUL",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                lineHeight = 38.09.sp,
                color = Color.Black,
                modifier = Modifier
                    .width(354.dp)
//                    .height(38.dp)
//                    .padding(top = 276.dp)
            )
            Spacer(modifier = Modifier
                .padding(20.dp)
            )
            Text(
                text = "The best simple place where you discover most wonderful furnitures and make your home beautiful",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                lineHeight = 35.sp,
                textAlign = TextAlign.Left,
                color = Color(0xFF595959),
                modifier = Modifier
                    .width(286.dp)
                    .align(alignment = Alignment.CenterHorizontally)
//                    .height(105.dp)
//                    .padding(top = 349.dp, start = 29.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 80.dp))
            Button(
                onClick = { navController.navigate("signin") },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
//                    .padding(top = 20.dp, start = 59.dp)
                    .width(159.dp)
                    .height(54.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White

                )
            }
        }
    }
}
