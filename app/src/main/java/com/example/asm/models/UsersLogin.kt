package com.example.asm.models

data class UsersLogin(
    val email: String,
    val fullName: String,
    val password: String,
    val phoneNumber: String,
    val id: Int,
    val avatar: String? = null
)