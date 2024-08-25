package com.example.asm.data

import com.example.asm.models.LoginRequest
import com.example.asm.models.User
import com.example.asm.models.product.FavoriteProduct
import com.example.asm.models.product.Products
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @POST("/api/users/login")
    suspend fun login(@Body request: LoginRequest) : User

    @GET("product")
    suspend fun GetAllProducts() : List<Products>

    @GET("product/{id}")
    suspend fun GetProductById(@Path("id") id:Int) : Products

    @GET("favorite")
    suspend fun GetAllFavorite(): List<FavoriteProduct>

    @DELETE("product/{productId}/favorite/{favoriteId}")
    suspend fun deleteFavoriteByProductIDAndFavoriteID(
        @Path("productId") productId: Int,
        @Path("favoriteId") favoriteId: Int
    )

    @POST("product/{productId}/favorite")
    suspend fun addFavoriteProduct(@Path("productId") productId: Int) : FavoriteProduct


}


