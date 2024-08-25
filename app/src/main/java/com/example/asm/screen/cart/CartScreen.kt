import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.asm.models.UserViewModel
import com.example.asm.models.cart.CartViewModel
import com.example.asm.models.cart.detailCartData
import com.example.asm.models.notification.NotificationViewModel
import com.example.asm.models.product.ProductViewModel
import com.example.asm.models.product.Products
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//data class CartItemData(val name: String, val price: Double, val quantity: Int, val imageUrl: Int)
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CartScreen(navController: NavController, userViewModel: UserViewModel, productViewModel: ProductViewModel, cartViewModel: CartViewModel) {
//
//
//    val user = userViewModel.user.observeAsState().value
//
//    user?.id?.let { cartViewModel.setup(it) }
//    val products = productViewModel.products.observeAsState().value ?: emptyList()
//    val cartItems = cartViewModel.cartData.observeAsState().value ?: emptyList()
//
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .padding(16.dp)) {
//        TopAppBar(
//            title = { Text("My Cart") },
//            navigationIcon = {
//                IconButton(onClick = { /* Handle back button press */ }) {
//                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
//                }
//            },
//            actions = {
//                IconButton(onClick = { /* Handle delete all button press */ }) {
//                    Icon(Icons.Default.Close, contentDescription = "Delete All")
//                }
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        LazyColumn {
//            items(cartItems) { item ->
//                CartItem(
//                    name = item.title,
//                    price = item.price,
//                    quantity = item.quantity,
//                    imageUrl = item.imageUrl
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = "",
//            onValueChange = { /* Handle promo code input changes */ },
//            label = { Text("Enter your promo code") },
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//            trailingIcon = {
//                IconButton(onClick = { /* Handle click on arrow icon */ }) {
//                    Icon(Icons.Default.ArrowForward, contentDescription = "Apply Promo Code")
//                }
//            }
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text("Total:", fontSize = 20.sp)
//            Text("$95.00", fontSize = 20.sp, color = Color.Black)
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = { /* Handle checkout */ },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Check out")
//        }
//    }
//}
//
//@Composable
//fun CartItem(name: String, price: Double, quantity: Int, imageUrl: Int) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Image(
//            painter = painterResource(id = imageUrl),
//            contentDescription = name,
//            modifier = Modifier.size(64.dp)
//        )
//
//        Spacer(modifier = Modifier.width(16.dp))
//
//        Column(modifier = Modifier.weight(1f)) {
//            Text(name, fontSize = 18.sp)
//            Text("$${price}", fontSize = 16.sp, color = Color.Gray)
//        }
//
//        Spacer(modifier = Modifier.width(16.dp))
//
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            IconButton(onClick = { /* Handle decrease quantity */ }) {
//                Icon(Icons.Default.Remove, contentDescription = "Decrease Quantity")
//            }
//            Text("$quantity", fontSize = 16.sp)
//            IconButton(onClick = { /* Handle increase quantity */ }) {
//                Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
//            }
//        }
//    }
//}
//
//

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CartScreen(
//    navController: NavController,
//    userViewModel: UserViewModel,
//    productViewModel: ProductViewModel,
//    cartViewModel: CartViewModel
//) {
//    val user = userViewModel.user.observeAsState().value
//    val products = productViewModel.products.observeAsState().value ?: emptyList()
//
//    // Thiết lập giỏ hàng với userID
//    user?.id?.let { cartViewModel.setup(it) }
//
//    // Lấy dữ liệu giỏ hàng hiện tại
//    val cartData = cartViewModel.cartData.observeAsState().value
//    val sumPrice = cartViewModel.cartData.observeAsState().value?.sumPrice ?: 0.0
//    val detailCart = cartViewModel.detailCartData.observeAsState().value
//    // Gọi hàm tạo cartData nếu chưa có
//    LaunchedEffect(cartData, products) {
//        cartData ?: cartViewModel.createCartData(cartViewModel.cart.value ?: return@LaunchedEffect, products)
//        cartViewModel.fetchDetailCartData(products)
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        TopAppBar(
//            title = { Text("My Cart") },
//            navigationIcon = {
//                IconButton(onClick = { navController.popBackStack() }) {
//                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
//                }
//            },
//            actions = {
//                IconButton(onClick = { /* Handle delete all button press */ }) {
//                    Icon(Icons.Default.Close, contentDescription = "Delete All")
//                }
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Render các mục giỏ hàng
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.7f)
//        ) {
//            detailCart?.let {
//                items(detailCart
//                ) { item ->
//                    renderCartItem(
//                        item = item,
//                        cartViewModel,
//                        products = products
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = "",
//            onValueChange = { /* Handle promo code input changes */ },
//            label = { Text("Enter your promo code") },
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//            trailingIcon = {
//                IconButton(onClick = { /* Handle click on arrow icon */ }) {
//                    Icon(Icons.Default.ArrowForward, contentDescription = "Apply Promo Code")
//                }
//            }
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text("Total:", fontSize = 20.sp)
//            Text(
//                "${sumPrice}",
//                fontSize = 20.sp,
//                color = Color.Black
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = { /* Handle checkout */ },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Check out")
//        }
//    }
//}
//
//@Composable
//fun renderCartItem(
//    item: detailCartData,
//    cartViewModel: CartViewModel,
//    products: List<Products>
//) {
//    val quantity = remember {
//        mutableStateOf(item.detailCart.quantity)
//    }
//    val cartData = cartViewModel?.cart?.observeAsState()?.value
//
//
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Image(
//            painter = rememberImagePainter(item.product.listImage[0]),
//            contentDescription = item.product.title,
//            modifier = Modifier.size(64.dp)
//        )
//
//        Spacer(modifier = Modifier.width(16.dp))
//
//        Column(modifier = Modifier.weight(1f)) {
//            Text(item.product.title, fontSize = 18.sp)
//            Text("$${item.product.price}", fontSize = 16.sp, color = Color.Gray)
//        }
//
//        Spacer(modifier = Modifier.width(16.dp))
//
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            IconButton(onClick = {
//                cartData?.let { cartViewModel.addOrUpdateCartDetail(cartData.id, -1, products, item.product) }
//                quantity.value -=1
//            }) {
//                Icon(Icons.Default.Remove, contentDescription = "Decrease Quantity")
//            }
//            Text("${quantity.value}", fontSize = 16.sp)
//            IconButton(onClick =
//            {
//                cartData?.let { cartViewModel.addOrUpdateCartDetail(cartData.id,  1, products, item.product) }
//                quantity.value += 1
//            }
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
//            }
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    notificationViewModel: NotificationViewModel
) {
    val user by userViewModel.user.observeAsState()
    val products by productViewModel.products.observeAsState(emptyList())
    val cartNow = cartViewModel.cartNow.observeAsState()
    val detailCart = cartNow?.value?.detailCart ?: emptyList()

    val currentTime = remember { LocalDateTime.now() }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val formattedTime = currentTime.format(formatter).toString()

    // Thiết lập giỏ hàng với userID
    user?.id?.let { cartViewModel.setupCart(it) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("My Cart") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Handle delete all button press */ }) {
                    Icon(Icons.Default.Close, contentDescription = "Delete All")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Render các mục giỏ hàng
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            items(detailCart) { item ->
                renderCartItem(
                    item = item,
                    cartViewModel = cartViewModel,
                    products = products
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle promo code input changes */ },
            label = { Text("Enter your promo code") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            trailingIcon = {
                IconButton(onClick = { /* Handle click on arrow icon */ }) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Apply Promo Code")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total:", fontSize = 20.sp)
            Text(
                "${String.format("%.2f", cartNow.value?.sumPrice)}", // Hiển thị số tiền với 2 chữ số thập phân
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                cartNow.value?.let {
                    notificationViewModel.addNotificationOrder(it, formattedTime)
//                    Log.e("log onclick", "CartScreen: "+cartNow.value, )
                }
                cartViewModel.order(formattedTime, user!!.id )


            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
            ,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text("Check out")
        }
    }
}

@Composable
fun renderCartItem(
    item: detailCartData,
    cartViewModel: CartViewModel,
    products: List<Products>
) {
    val listCart = cartViewModel.cartNow.observeAsState().value?.detailCart
    val cartItem = listCart?.find { it.product.id == item.product.id }
//    Log.e("Data reLoad", "renderCartItem: "+item?.detailCart, )
//    val currentCart = cartViewModel.cartNow.observeAsState().value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(item.product.listImage.firstOrNull()),
            contentDescription = item.product.title,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.product.title, fontSize = 18.sp)
            Text("$${item.product.price}", fontSize = 16.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (item.detailCart.quantity > 1) {
                    cartViewModel.updateProductQuantity(item.product.id, -1, item.product.price)
//                    quantity -= 1
                } else{
                    cartViewModel.removeProductFromCart(item.product.id)
                }
            }) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease Quantity")
            }
            Text("${cartItem?.detailCart?.quantity}", fontSize = 16.sp)
            IconButton(onClick = {
                cartViewModel.updateProductQuantity(item.product.id, +1, item.product.price)
//                Log.e("Test update", "renderCartItem: "+item, )
//                quantity.value += 1
            }) {
                Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
            }
        }
    }
}

