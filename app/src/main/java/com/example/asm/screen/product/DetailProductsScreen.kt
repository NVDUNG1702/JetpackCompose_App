package com.example.asm.screen.product

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.asm.models.product.ProductViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.asm.models.UserViewModel
import com.example.asm.models.cart.CartViewModel

@Composable
fun DetailProductScreen(navController: NavController, productViewModel: ProductViewModel, idProduct: Int, cartViewModel: CartViewModel, userViewModel: UserViewModel) {

    val product = productViewModel.products.value?.find { it.id == idProduct }
    val products = productViewModel.products.observeAsState().value
    val scrollState = rememberScrollState()
    var quantity by remember { mutableStateOf(1) }
    val scrollDetailState = rememberScrollState()
    val favoriteProductIds = productViewModel.favoriteProductIds.observeAsState();
    val user = userViewModel.user.observeAsState().value
    val cart = cartViewModel.cartNow.observeAsState().value
    user?.id?.let { cartViewModel.setupCart(it) }
    var isFavorite = (productViewModel.productsFavorite.observeAsState().value?.any{ item-> item.id == idProduct })

    LaunchedEffect(Unit) {
        user?.id?.let { cartViewModel.setupCart(it) }
    }

    if (product != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with Back Button
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack()}) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(35.dp))
                }

            }

            // Image Slider
            ProductImageSlider(images = product!!.listImage)

            // Product Title
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = product!!.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)

            // Price and Quantity
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Price: \$${product!!.price}", fontSize = 18.sp, modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { if (quantity > 1) --quantity },
                        modifier = Modifier
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(10.dp))


                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Decrease Quantity",
                            modifier = Modifier,
                            tint = Color.White
                        )
                    }
                    Text(text = quantity.toString(),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                    IconButton(
                        onClick = { quantity = quantity+1 },
                        modifier = Modifier
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(10.dp))

                            ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase Quantity", tint = Color.White)
                    }
                }
            }

            // Product Details
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .verticalScroll(scrollDetailState)
            ) {
                Text(text = product!!.detail, fontSize = 16.sp)
            }

            // Actions: Add to Favorites and Add to Cart
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {

//                        isFavorite?.let { isFavorite = it }
                              if(isFavorite == true){
                                  val favoriteProductRemove = favoriteProductIds?.value?.find { it.productId == product.id}
                                  favoriteProductRemove?.let { productViewModel.deleteFavoriteProductByID(favoriteProductRemove.id, favoriteProductRemove.productId) }
//                                  isFavorite.value = false
                              } else{
                                  productViewModel.addFavoriteProduct(product.id)
//                                  isFavorite.value = true
                              }
//                        Log.e("list favorite", "size:"+productViewModel.productsFavorite.value?.size.toString() )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .fillMaxHeight()
                    ,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE0E0E0)
                    )

                ) {
//                    Log.e("Check", "DetailProductScreen: ${isFavorite}", )
                    Icon(imageVector = if(isFavorite == true)Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder, contentDescription = "", modifier = Modifier.size(35.dp), tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {


                            cart?.let { products?.let { cartViewModel.addProductToCart(product, quantity, product.price) } }


                    },
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight()
                    ,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )

                ) {
                    Text("Add to Cart", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    } else {
        // Display something if the product is not found
        Text(text = "Product not found ${""}", modifier = Modifier.fillMaxSize(), color = Color.Red)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImageSlider(images: List<String>) {
    // This is a basic implementation; you may want to use a library for image slider
    val pagerState = rememberPagerState(pageCount = { images.size })
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {page ->
        // Replace with your image loading logic
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)) {
            // Replace with actual image content
            Image(
                painter = rememberImagePainter(images[page]),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewDetailProductScreen() {
//    DetailProductScreen(
//        productViewModel = ProductViewModel(), // Provide a mock or real ProductViewModel instance
//        idProduct = 1
//    )
//}
