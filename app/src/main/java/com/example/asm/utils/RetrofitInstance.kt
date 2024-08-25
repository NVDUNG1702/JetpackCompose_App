package com.example.asm.utils

import com.example.asm.data.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstanceLogin {
    val api : ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}

object RetrofitInstanceProduct {
    val api : ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.ProductBase_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}
