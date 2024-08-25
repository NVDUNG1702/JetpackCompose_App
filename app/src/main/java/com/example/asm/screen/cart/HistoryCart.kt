package com.example.asm.screen.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.asm.models.UserViewModel
import com.example.asm.models.cart.CartViewModel
import com.example.asm.models.cart.cartData
import com.example.asm.models.product.ProductViewModel
data class Order(
    val id: Int,
    var timeOrder: String? = null,
    val quantity: Int,
    val sumPrice: Double,
    val statusOrder: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryCartScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel
) {
    val orders = listOf(
        Order(238562312, "20/03/2020", 3, 150.0, "Delivered"),
        Order(238562313, "21/03/2020", 2, 100.0, "Processing"),
        Order(238562314, "22/03/2020", 1, 50.0, "Canceled")
    )

    val carts = cartViewModel.cartData.observeAsState().value
    val orderList = carts?.filter { it.cart.status == true && it.cart.idUser == userViewModel.user.observeAsState().value?.id }

    val isTab = remember { mutableStateOf(0) }

    Column {
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

        TabRow(selectedTabIndex = isTab.value) {
            Tab(selected = isTab.value == 0, onClick = { isTab.value = 0 }) {
                Text("Đang xử lý", modifier = Modifier.padding(10.dp))
            }
            Tab(selected = isTab.value == 1, onClick = { isTab.value = 1 }) {
                Text("Thành công", modifier = Modifier.padding(10.dp))
            }
            Tab(selected = isTab.value == 2, onClick = { isTab.value = 2 }) {
                Text("Canceled", modifier = Modifier.padding(10.dp))
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            items(orderList?.filter { order ->
                when (isTab.value) {
                    0 -> order.cart.statusOrder == "Processing"
                    1 -> order.cart.statusOrder == "Delivered"
                    2 -> order.cart.statusOrder == "Canceled"
                    else -> false
                }
            } ?: emptyList()) { order ->  // Nếu orderList là null, sử dụng danh sách rỗng
            OrderItem(order)
        }
        }
    }
}

@Composable
fun OrderItem(order: cartData) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .padding(10.dp)



        ,
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(2.dp, Color.White),

    ) {
        Text(text = "Order No: ${order.cart.id}")
        Text(text = "Date: ${order.cart.timeOrder}")
        Text(text = "Quantity: ${order.detailCart.size}")
        Text(text = "Total Amount: $${order.sumPrice}")
        Row {
            Button(onClick = { /*TODO*/ }) {
                Text("Detail")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = order.cart.statusOrder.toString())
        }
    }
}
