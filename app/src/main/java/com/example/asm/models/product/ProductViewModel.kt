package com.example.asm.models.product

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm.utils.RetrofitInstanceProduct
import kotlinx.coroutines.launch



class ProductViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Products>>()
    val products: LiveData<List<Products>> get() = _products

    private val _productsFavorite = MutableLiveData<List<Products>>()
    val productsFavorite: LiveData<List<Products>> get() = _productsFavorite

    private val _favoriteProductIds = MutableLiveData<List<FavoriteProduct>>()
    val favoriteProductIds: LiveData<List<FavoriteProduct>> get() = _favoriteProductIds

    private val _product = MutableLiveData<Products?>()
    val product: LiveData<Products?> get() = _product

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    init {
        // Khởi tạo danh sách sản phẩm yêu thích rỗng
        _productsFavorite.value = emptyList()
        _loading.value = false
    }
    fun fetchAllProducts() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = RetrofitInstanceProduct.api.GetAllProducts()
                _products.value = response
                Log.e("data", "fetchAllProducts: "+response, )
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("Error", "fetchAllProducts: "+e.message, )
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchProductById(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstanceProduct.api.GetProductById(id)
                _product.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

//    favorite
    fun fetchFavoriteProduct(){
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = RetrofitInstanceProduct.api.GetAllFavorite()

                _favoriteProductIds.value = response
//                Log.e("Error Fetch Favorite", "res: "+response.toString() )
                updateFavoriteProducts()
            } catch (e: Exception){
//                Log.e("Error Fetch Favorite", e.message.toString() )
            } finally {
                _loading.value = false
            }
        }
    }

    private fun updateFavoriteProducts() {
        val favoriteIds = favoriteProductIds.value?.map { it.productId }
        val allProducts = products.value
//        Log.e("Error Fetch Favorite", favoriteIds.toString() )

        if (favoriteIds != null && allProducts != null) {
            val favoriteProducts = allProducts.filter { it.id in favoriteIds }
            _productsFavorite.value = favoriteProducts
        }
    }

//    delete
    fun deleteFavoriteProductByID(id: Int, idProduct: Int) {
        viewModelScope.launch {
            try {
                RetrofitInstanceProduct.api.deleteFavoriteByProductIDAndFavoriteID(idProduct, id)
                fetchFavoriteProduct()
            } catch (e: Exception){
//                Log.e("Error Delete", "deleteFavoriteProductByID: "+e.message, )
            }
        }
    }

//    add
    fun addFavoriteProduct(idProduct: Int){
        viewModelScope.launch {

            try {
                val checkNull = _favoriteProductIds?.value?.find { it.productId == idProduct }
                if(checkNull == null){
                    val response = RetrofitInstanceProduct.api.addFavoriteProduct(idProduct)
                    if(response != null){
                        fetchFavoriteProduct()
                    }
                }
            } catch (e: Exception){

            }
        }
    }


    fun addProductFavorite(productsNew: Products){
        val checkProduct = products.value?.find { productsNew.id == it.id }
        if(checkProduct != null){
            val currentList = _productsFavorite.value.orEmpty()

            // Tạo danh sách mới với sản phẩm đã thêm
            val updatedList = currentList + productsNew

            _productsFavorite.value = updatedList
        }
    }

    fun removeProductFavorite(productRemove: Products) {
        // Lấy danh sách hiện tại từ LiveData
        val currentList = _productsFavorite.value.orEmpty().toMutableList()

        // Tạo danh sách mới bằng cách lọc bỏ sản phẩm có id tương ứng
        currentList.remove(productRemove)

        // Cập nhật giá trị mới vào _productsFavorite
        _productsFavorite.value = currentList
    }

    fun isProductFavorite(productId: Int): Boolean {
        // Lấy danh sách hiện tại từ LiveData
        val currentList = _productsFavorite.value.orEmpty()

        // Kiểm tra nếu danh sách chứa sản phẩm với id tương ứng
        return currentList.any { it.id == productId }
    }




}
