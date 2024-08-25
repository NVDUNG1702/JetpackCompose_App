package com.example.asm.models

data class User(
    val ACCESS_TOKEN: ACCESSTOKEN,
    val REFRESH_TOKEN: REFRESHTOKEN,
    val status: Int,
    val user: UserX
)