package com.example.asm.screen.home


import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.ChairAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Chair
import androidx.compose.material.icons.outlined.ChairAlt
import androidx.compose.material.icons.outlined.Light
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.TableRestaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.request.Tags
import com.example.asm.models.UserViewModel
import com.example.asm.models.product.ProductViewModel
import com.example.asm.models.product.Products
//dungsenpai01101975@gmail.com
@Composable
fun HomeScreen(navController: NavHostController, userViewModel: UserViewModel = viewModel(), productViewModel: ProductViewModel = viewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    // Load user data from SharedPreferences or network call
    val user = userViewModel.user

    val avatar = user.value?.avatar;

    val image = replaceLocalhostWithIp(avatar.toString(), "192.168.1.10");

//
    var isSearching = remember { mutableStateOf(false) }
    var (searchText, onSearchTextChange)  = remember { mutableStateOf("") }
    val (typeFocused, setTypeFocused) = remember { mutableStateOf(1) }

    val isLoadingProduct = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
        ,
//        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        user?.let {
//            Box(
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape)
//
//            ){
//                Image(
//                    painter = rememberImagePainter(image),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(200.dp)
//                        .clip(CircleShape)
//                )
//            }
//
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(text = "Welcome, ${user.value?.fullName}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(text = "Phone: ${user.value?.phoneNumber}", fontSize = 16.sp)
//        } ?: run {
//            CircularProgressIndicator()
//        }
        Header(
            isSearching = isSearching.value,
            onSearchClicked = { isSearching.value = true },
            onCloseSearchClicked = { isSearching.value = false; searchText = "" },
            searchText = searchText,
            onSearchTextChange = onSearchTextChange,
            navController
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProductTypeMenu(typeFocused, setTypeFocused)
        Spacer(modifier = Modifier.height(16.dp))
        ProductList(productViewModel, focusedType = typeFocused, isSearching.value, searchText, navController)
    }
}



fun replaceLocalhostWithIp(url: String, ip: String): String {
    return url.replace("http://localhost:1702", ip)
}

@Composable
fun Header(
    isSearching: Boolean,
    onSearchClicked: () -> Unit,
    onCloseSearchClicked: () -> Unit,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    navController: NavController
) {
    Spacer(modifier = Modifier.height(50.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSearching) {
            IconButton(onClick = onCloseSearchClicked) {
                Icon(Icons.Default.Close, contentDescription = "Close Search", modifier = Modifier.size(35.dp))
            }
            OutlinedTextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                label = { Text(text = "Find my product")}
            )
        } else {
            IconButton(onClick = onSearchClicked) {
                Icon(Icons.Filled.Search, contentDescription = "Search", modifier = Modifier.size(35.dp))
            }

            Column {
                Row {
                    Text(text = "Make home", fontSize = 18.sp, fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
                }
                Row {
                    Text(text = "BEAUTIFUL", fontSize = 18.sp, fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
                }
            }
            IconButton(onClick = { navController.navigate("cart") }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", modifier = Modifier.size(35.dp))
            }
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}

data class dataMenuItem (
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val icon_outline: ImageVector
)



@Composable
fun ProductTypeMenu( focusedType: Int, onTypeSelected: (Int) -> Unit) {

    val scrollState = rememberScrollState()

    val listMenu = listOf(
        dataMenuItem(1, "Popular", Icons.Filled.Star, Icons.Outlined.Star),
        dataMenuItem(2, "Chair", Icons.Filled.ChairAlt, Icons.Outlined.ChairAlt),
        dataMenuItem(3, "table",Icons.Filled.TableRestaurant, Icons.Outlined.TableRestaurant),
        dataMenuItem(4, "Armchair", Icons.Filled.Chair, Icons.Outlined.Chair),
        dataMenuItem(5, "Bed", Icons.Filled.Bed, Icons.Outlined.Bed),
        dataMenuItem(6, "Lamb", Icons.Filled.Light, Icons.Outlined.Light)
    )
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .fillMaxWidth(0.9f)
    ) {
        listMenu.forEach { type ->
            var isSelected = type.id == focusedType

            Card(
                elevation = if(isSelected)CardDefaults.cardElevation(10.dp) else CardDefaults.cardElevation(5.dp),
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                ,
                onClick = {
                    onTypeSelected(type.id)
                },
                colors = CardDefaults.outlinedCardColors(
                    containerColor = if (isSelected) Color(0xFFd9d9d9) else Color(0xFFf2f2f2),
                ),
                border = if(isSelected)BorderStroke(2.dp, Color.White)else BorderStroke(0.dp, Color.White)

            )
            {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Icon(
                        imageVector = if(isSelected)type.icon else type.icon_outline,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterHorizontally)

                    )
                    Text(
                        text = type.title,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

            }
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

@Composable
fun ProductList( productViewModel: ProductViewModel, focusedType: Int, isSearching: Boolean, searchText: String, navController: NavController) {

    val products = productViewModel.products.observeAsState(emptyList())
    val loading = productViewModel.loading.observeAsState(false)
    val error = productViewModel.error.observeAsState("")

    LaunchedEffect(Unit) {
        productViewModel.fetchAllProducts()
    }

    val listFilter = products.value.filter {
            it.type == focusedType || focusedType == 1
    }
    val listFilterSearch = listFilter.filter {
        it.title.contains(searchText, ignoreCase = true)
    }



    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(0.85f)
    ) {
        if(loading.value){
            CircularProgressIndicator()
        } else{
            LazyVerticalGrid(
                modifier = Modifier

                ,
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
//                verticalArrangement = Arrangement.spacedBy(10.dp),

            ) {
                if(!isSearching)items(listFilter){ product -> ProductItem(product = product, navController) }  else items(listFilterSearch){ product -> ProductItem(product = product, navController) }
            }
        }
    }
}

@Composable
fun ProductItem(product: Products, navController: NavController) {
    Card(
//        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .height(250.dp)
            ,
//        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
//            .background(Color.Black)
        onClick = {
            try {
                navController.navigate("detailProduct/${product.id}")
            } catch (e: Exception) {
                Log.d("Lỗi navigate", "ProductItem: ${e}")
            }

        }

    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
        ) {
            Image(
                painter = rememberImagePainter(product.listImage[0]),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp))
                ,
                contentScale = ContentScale.FillWidth
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = product.title, fontSize = 18.sp)
            Text(text = "\$${product.price}", fontSize = 14.sp, color = Color.Gray)
        }
    }
}


//dungsenpai01101975@gmail.com