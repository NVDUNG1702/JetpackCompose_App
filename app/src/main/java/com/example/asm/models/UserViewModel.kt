package com.example.asm.models

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserViewModel(): ViewModel() {
    // LiveData hoặc MutableState để giữ dữ liệu người dùng
    private val _user = MutableLiveData<UserX?>()
    val user: LiveData<UserX?> = _user

    init {
        // Tải dữ liệu người dùng từ API hoặc SharedPreferences khi ViewModel được khởi tạo

    }

    fun setUser(userX: UserX?) {
        _user.value = userX
    }


}
