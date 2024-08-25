package com.example.asm.screen.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.asm.models.notification.NotificationViewModel
import com.example.asm.models.notification.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NotificationScreen(
    navController: NavHostController,
    notificationViewModel: NotificationViewModel = viewModel()
) {
    val notifications = notificationViewModel.notification.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBackIosNew,
                    contentDescription = "back",
                    modifier = Modifier.size(35.dp)
                )
            }

            Column {
                Row {
                    Text(
                        text = "My Order",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    )
                }

            }
            IconButton(onClick = { /* TODO: handle cart click */ }) {

            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
        ) {
            items(notifications.value) { notification ->
                NotificationItem(notification, notificationViewModel)
            }
        }
    }
}

@Composable
fun NotificationItem(notification: notification, notificationViewModel: NotificationViewModel) {
    // Trạng thái để điều khiển hiển thị content
    val isExpanded = remember { mutableStateOf(false) }

    // Chọn kiểu chữ dựa trên trạng thái của thông báo
    val titleStyle = if (notification.status) {
        MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
    } else {
        MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                isExpanded.value = !isExpanded.value
                notificationViewModel.update(notification.id)
            },
        elevation = CardDefaults.cardElevation(4.dp),

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = notification.title,
                style = titleStyle, // Áp dụng kiểu chữ dựa trên trạng thái
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (isExpanded.value) {
                Text(
                    text = notification.content,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

    }
}

