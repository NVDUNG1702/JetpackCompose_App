package com.example.asm.models.product

data class Products(
    val detail: String,
    val id: Int,
    val listImage: List<String>,
    val price: Float,
    val quantity: Int,
    val title: String,
    val type: Int
)