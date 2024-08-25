package com.example.asm.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.asm.models.UserViewModel
import com.example.asm.models.cart.CartViewModel
import com.example.asm.models.product.ProductViewModel
import com.example.asm.models.product.Products
import kotlinx.coroutines.delay

@Composable
fun FavoriteScreen(navController: NavController, productViewModel: ProductViewModel, cartViewModel: CartViewModel, userViewModel: UserViewModel) {

    val favoriteProducts = productViewModel.productsFavorite.observeAsState()
    val favoriteProductIds = productViewModel.favoriteProductIds.observeAsState()
    var isLoading = productViewModel.loading.observeAsState(false)
    val cart = cartViewModel.cartNow.observeAsState().value
    userViewModel.user.value?.let { cartViewModel.setupCart(it.id) }
    val isSearch = remember {
        mutableStateOf(false)
    }
    val textSearch = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        productViewModel.fetchFavoriteProduct()
    }

//
    // Hàng đầu tiên với TextField, tiêu đề và nút giỏ hàng
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)

    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isSearch.value) {
                IconButton(onClick = { isSearch.value = !isSearch.value }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close Search",
                        modifier = Modifier.size(35.dp)
                    )
                }
                OutlinedTextField(
                    value = textSearch.value,
                    onValueChange = { textSearch.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),

                    label = { Text(text = "Find my product") }
                )
            } else {
                IconButton(onClick = { isSearch.value = !isSearch.value }) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(35.dp)
                    )
                }

                Column {
                    Row {
                        Text(
                            text = "Make home",
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center
                        )
                    }
                    Row {
                        Text(
                            text = "BEAUTIFUL",
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                IconButton(onClick = { /* TODO: handle cart click */ }) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }

        // Danh sách sản phẩm yêu thích
        if(!isLoading.value){
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp)
            ) {


                if(favoriteProducts?.value != null){
                    favoriteProducts?.let {
                        items(favoriteProducts.value!!) { product ->
                            FavoriteProductItem(
                                product = product,
                                onRemoveClick = {
                                    val favoriteRemove = favoriteProductIds?.value?.find { it.productId == product.id }
                                    favoriteRemove?.let { productViewModel.deleteFavoriteProductByID(it.id, it.productId) }
                                }
                            )
                        }
                    }

                }

            }

            // Nút thêm tất cả vào giỏ hàng
            if(favoriteProducts?.value != null && favoriteProducts?.value?.size != 0){
                favoriteProducts?.let {
                    Button(
                        onClick = {
                            val products = favoriteProducts.value
                            if (products != null) {
                                cartViewModel.addListProductToCart(products)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        )
                    ) {
                        Text("Add All to Cart")
                    }
                }
            }
        } else{
            CircularProgressIndicator()
        }
        Spacer(modifier = Modifier.height(120.dp))
    }

}
//}

@Composable
fun FavoriteProductItem(product: Products, onRemoveClick: (Products) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            // Cột hình ảnh
            Image(
                painter = rememberImagePainter(product.listImage.firstOrNull()), // Giả sử chỉ có 1 ảnh để hiển thị
                contentDescription = product.title,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            )

            // Cột tiêu đề và giá
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(text = product.title, style = MaterialTheme.typography.titleLarge)
                Text(text = "${product.price} USD", style = MaterialTheme.typography.titleLarge)
            }

            // Cột nút xóa
            IconButton(onClick = { onRemoveClick(product) }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Remove")
            }
        }
    }
}
//