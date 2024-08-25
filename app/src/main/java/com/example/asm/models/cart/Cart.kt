package com.example.asm.models.cart

import com.example.asm.models.product.Products


data class detailCart(
    val idCart: Int,
    val id: Int,
    val idProduct: Int,
    var quantity: Int,
    var sumPrice: Float
) {
    fun calcSumPrice(price: Float){
        this.sumPrice = this.quantity * price
    }
}
data class cart(
    val id: Int,
    val idUser: Int,
    var status: Boolean,
    var timeOrder: String? = null,
    var statusOrder: String? = null


)
data class detailCartData(
    val detailCart: detailCart,
    val product: Products
)
data class cartData(
    val cart: cart,
    val detailCart: MutableList<detailCartData>,
    var sumPrice: Float,
) {
    fun calcSumPrice(){
        var sum = 0f
        this.detailCart.forEach { data -> sum += data.detailCart.sumPrice }
        this.sumPrice = sum
    }
}
