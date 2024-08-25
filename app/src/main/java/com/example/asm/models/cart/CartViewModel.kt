//package com.example.asm.models.cart
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.asm.models.product.Products
//
//class CartViewModel: ViewModel() {
//    val _carts = MutableLiveData<List<cart>>()
//    val carts: LiveData<List<cart>> = _carts
//
//    val _cartNow = MutableLiveData<cart?>()
//    val cart: LiveData<cart?> = _cartNow
//
//    val _cartDetails = MutableLiveData<List<detailCart>>()
//    val cartDetails: LiveData<List<detailCart>> = _cartDetails
//
//    val _detailCartData = MutableLiveData<List<detailCartData>>()
//    val detailCartData: LiveData<List<detailCartData>> = _detailCartData
//
//    private val _cartData = MutableLiveData<cartData?>()
//    val cartData: LiveData<cartData?> = _cartData
//
//    fun setup(userID: Int){
//        // Tìm kiếm cart có status = false và idUser = userID
//        val existingCart = carts.value?.find { cart -> cart.status == false && cart.idUser == userID }
//
//        if (existingCart != null) {
//            // Nếu tìm thấy cart, set nó vào _cartNow
//            _cartNow.value = existingCart
//        } else {
//            // Nếu không tìm thấy, tạo một cart mới
//            val intID = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
//            val cartNew = cart(
//                idUser = userID,
//                status = false,
//                id = intID
//            )
//            _cartNow.value = cartNew
//        }
//
//    }
//
//    fun fetchDetailCartData(products: List<Products?>){
//        val cartDetailsList = cartDetails.value ?: emptyList()
//
//        val listCartDetail = cartDetailsList.mapNotNull { detailCart ->
//            val product = products.find { it?.id == detailCart.idProduct }
//            product?.let {
//                detailCartData(detailCart, it)
//            }
//        }
//        _detailCartData.value = listCartDetail
//    }
//
//fun addOrUpdateCartDetail(cartID: Int, newQuantity: Int, products: List<Products?>, product: Products) {
//    val cartDetailsList = _cartDetails.value?.toMutableList() ?: mutableListOf()
//
//    // Check if the cart item already exists
//    val existingDetailCart = cartDetailsList.find { it.idProduct == product.id && it.idCart == cartID }
//
//    if (existingDetailCart != null) {
//
//        // If the item exists, update the quantity
//        existingDetailCart.quantity += newQuantity
//
//        // Update the sum price based on the new quantity
//        product?.let {
//            existingDetailCart.calcSumPrice(it.price)
//        }
//
//    } else {
//        // If the item doesn't exist, add it to the cart
//        val intID = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
//        val newDetailCart = detailCart(
//            id = intID,
//            idProduct = product.id,
//            quantity = newQuantity,
//            sumPrice = 0f,
//            idCart = cartID
//        )
//        newDetailCart.calcSumPrice(product.price)
//        cartDetailsList.add(newDetailCart)
//    }
//
//    // Update the LiveData with the modified list
//    _cartDetails.value = cartDetailsList
//
//
//    // Refresh the detailCartData
//    products?.let { fetchDetailCartData(products) }
//    Log.e("data cart", "addOrUpdateCartDetail: "+ cartDetails.value, )
//    loadCartData()
//}
//
//
//    fun createCartData(cart: cart, products: List<Products>) {
//        fetchDetailCartData(products)
//
//        val detailCartDataList = _detailCartData.value ?: emptyList()
//        val cartData = cartData(
//            cart = cart,
//            detailCart = detailCartDataList,
//            sumPrice = 0f
//        )
//        cartData.calcSumPrice()
//
//        _cartData.value = cartData
//    }
//
//    fun loadCartData(){
//        val cartDetailsList = _detailCartData.value?.toMutableList() ?: mutableListOf()
//
//        // Check if the cart item already exists
//        val existingDetailCart = cartDetailsList.filter { it.detailCart.idCart == _cartNow.value?.id }
//        var cartDt = _cartNow?.value?.let {
//            cartData(
//                cart = it,
//                detailCart = existingDetailCart,
//                sumPrice = 0f
//            )
//        }
//
//        _cartData.value = cartDt
//        Log.e("DATA existingDetailCart", "loadCartData: "+existingDetailCart, )
//    }
//
//
//}



//    fun addItemToCart(product: Products, quantity: Int) {
//        val currentCart = _cartNow.value ?: return
//        val currentDetails = _cartDetails.value?.toMutableList() ?: mutableListOf()
//
//        val existingItem = currentDetails.find { it.idProduct == product.id }
//
//        if (existingItem != null) {
//            existingItem.quantity += quantity
//        } else {
//            val intID = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
//            val newItem = detailCart(
//                id = intID,
//                idProduct = product.id,
//                quantity = quantity,
//                sumPrice = 0f, // Calculate sum price if needed,
//                idCart =
//            )
//            newItem.calcSumPrice(product.price)
//            currentDetails.add(newItem)
//        }
//
//        _cartDetails.value = currentDetails
//        fetchDetailCartData(listOf()) // Pass the appropriate products list
//    }




//    fun updateCartDetail(detailCartData: detailCartData, newQuantity: Int, products: List<Products>) {
//        val cartDetailsList = _cartDetails.value?.toMutableList() ?: mutableListOf()
//
//        // Tìm và cập nhật detailCart
//        val detailCartToUpdate = cartDetailsList.find { it.idProduct == detailCartData.product.id }
//        detailCartToUpdate?.let { detailCart ->
//            detailCart.quantity = newQuantity
//
//            // Tìm sản phẩm tương ứng để tính toán lại sumPrice
//            val product = products.find { it.id == detailCartData.product.id }
//            product?.let {
//                detailCart.calcSumPrice(it.price)
//            }
//
//            // Cập nhật lại danh sách cartDetails
//            _cartDetails.value = cartDetailsList
//
//            // Tải lại detailCartData
//            fetchDetailCartData(products)
//        }
//    }



package com.example.asm.models.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asm.models.product.Products

class CartViewModel : ViewModel() {
    // Lưu trữ trạng thái của giỏ hàng
    private val _cartData = MutableLiveData<List<cartData>>()
    val cartData: LiveData<List<cartData>> get()  = _cartData

    private val _cartNow = MutableLiveData<cartData?>()
    val cartNow: LiveData<cartData?> get() = _cartNow

    fun loadingData(){
        _cartNow.value = null
//        cartNow.value = null
        val currentCart = _cartNow.value?.copy()
        _cartNow.value = currentCart
    }

//    setup
    fun setupCart(idUser: Int){
        val cartNow = _cartData.value?.find { item ->
            item.cart.status == false && item.cart.idUser == idUser
        }

    if (cartNow != null) {
        // Nếu có giỏ hàng phù hợp, cập nhật cartNow
        _cartNow.value = cartNow
    } else {
        // Nếu không có giỏ hàng, tạo mới và thêm vào danh sách
        val newCart = cartData(
            cart = cart(id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(), idUser = idUser, status = false),
            detailCart = mutableListOf(), // Khởi tạo danh sách chi tiết giỏ hàng
            sumPrice = 0f
        )

        // Cập nhật danh sách giỏ hàng và giỏ hàng hiện tại
        val updatedCartData = _cartData.value?.toMutableList() ?: mutableListOf()
        updatedCartData.add(newCart)
        _cartData.value = updatedCartData.toList()

        _cartNow.value = newCart
    }
    }


    // Thêm sản phẩm vào giỏ hàng
    fun addProductToCart(product: Products, quantity: Int, price: Float) {
        val currentCart = _cartNow.value ?: return
        if (currentCart.cart.status) {
            // Nếu giỏ hàng đã thanh toán, không cho phép thêm sản phẩm
            return
        }

        val existingDetailCart = currentCart.detailCart.find { it.product.id == product.id }

        if (existingDetailCart != null) {

            // Tăng số lượng sản phẩm nếu đã tồn tại trong giỏ hàng
//            existingDetailCart.detailCart.quantity += quantity
//            existingDetailCart.detailCart.calcSumPrice(price)

            updateProductQuantity(product.id, quantity, price)
        } else {
            // Thêm sản phẩm mới vào giỏ hàng
//            Log.e("Test add", "addProductToCart: ok", )
            val intID = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
            val newDetailCart = detailCartData(
                detailCart = detailCart(
                    idCart = currentCart.cart.id,
                    id = intID,
                    idProduct = product.id,
                    quantity = quantity,
                    sumPrice = quantity * price
                ),
                product = product
            )
            currentCart.detailCart.add(newDetailCart)
            currentCart.calcSumPrice()
            _cartNow.value = currentCart

            // Cập nhật danh sách giỏ hàng
            _cartData.value = _cartData.value?.map { cart ->
                if (cart.cart.id == currentCart.cart.id) currentCart else cart
            }?.toMutableList()
            loadingData()
        }

        // Tính lại tổng giá trị của giỏ hàng
    }

    fun addListProductToCart(products: List<Products>) {
        val currentCart = _cartNow.value ?: return
        if (currentCart.cart.status) {
            // Nếu giỏ hàng đã thanh toán, không cho phép thêm sản phẩm
            return
        }

        for (product in products) {
            val existingDetailCart = currentCart.detailCart.find { it.product.id == product.id }

            if (existingDetailCart != null) {
                // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
                updateProductQuantity(product.id, existingDetailCart.detailCart.quantity + 1, product.price)
            } else {
                // Nếu sản phẩm chưa tồn tại, tạo mới và thêm vào giỏ hàng
                val intID = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
                val newDetailCart = detailCartData(
                    detailCart = detailCart(
                        idCart = currentCart.cart.id,
                        id = intID,
                        idProduct = product.id,
                        quantity = 1,  // Số lượng là 1
                        sumPrice = product.price  // Giá sản phẩm
                    ),
                    product = product
                )
                currentCart.detailCart.add(newDetailCart)
            }
        }

        // Tính lại tổng giá trị của giỏ hàng
        currentCart.calcSumPrice()
        _cartNow.value = currentCart

        // Cập nhật danh sách giỏ hàng
        _cartData.value = _cartData.value?.map { cart ->
            if (cart.cart.id == currentCart.cart.id) currentCart else cart
        }?.toMutableList()
        loadingData()
    }


//

    // Sửa số lượng sản phẩm trong giỏ hàng
    fun updateProductQuantity(productId: Int, newQuantity: Int, price: Float) {
        val currentCart = _cartNow.value ?: return
        if (currentCart.cart.status) {
            // Nếu giỏ hàng đã thanh toán, không cho phép sửa sản phẩm
            return
        }

        val detailCart = currentCart.detailCart.find { it.product.id == productId } ?: return

        detailCart.detailCart.quantity += newQuantity
        detailCart.detailCart.calcSumPrice(price)

        // Tính lại tổng giá trị của giỏ hàng
        currentCart.calcSumPrice()
        _cartNow.value = currentCart

        // Cập nhật danh sách giỏ hàng
        _cartData.value = _cartData.value?.map { cart ->
            if (cart.cart.id == currentCart.cart.id) currentCart else cart
        }?.toMutableList()
        loadingData()
    }

    // Xoá sản phẩm khỏi giỏ hàng
    fun removeProductFromCart(productId: Int) {
        val currentCart = _cartNow.value ?: return
        if (currentCart.cart.status) {
            // Nếu giỏ hàng đã thanh toán, không cho phép xóa sản phẩm
            return
        }

        val updatedDetailCart = currentCart.detailCart
            .filterNot { it.product.id == productId }
            .toMutableList()

        // Cập nhật danh sách sản phẩm trong giỏ hàng
        currentCart.detailCart.clear()
        currentCart.detailCart.addAll(updatedDetailCart)

        // Tính lại tổng giá trị của giỏ hàng
        currentCart.calcSumPrice()
        _cartNow.value = currentCart

        // Cập nhật danh sách giỏ hàng
        _cartData.value = _cartData.value?.map { cart ->
            if (cart.cart.id == currentCart.cart.id) currentCart else cart
        }?.toMutableList()
        loadingData()
    }

    fun order(formattedTime: String, UID: Int){

        val currentCart = _cartNow.value ?: return
        currentCart.cart.status = true
        currentCart.cart.timeOrder = formattedTime
        currentCart.cart.statusOrder = "Processing"
        _cartNow.value = currentCart

        _cartData.value = _cartData.value?.map { cart ->
            if (cart.cart.id == currentCart.cart.id) currentCart else cart
        }
        setupCart(UID)
        loadingData()

//        Log.e("Data carts", "order: "+_cartData.value, )
    }
}