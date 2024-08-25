package com.example.asm.models.notification

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asm.models.cart.cartData


data class notification(
    val id: Int,
    val status: Boolean,
    var title: String,
    var content: String
)

class NotificationViewModel: ViewModel() {
    private val _notifications = MutableLiveData<List<notification>>()
    val notification: LiveData<List<notification>> get()  = _notifications

    fun addNotificationOrder(cartData: cartData, time: String) {
        var content = ""
        var soSanPham = "Số sản phẩm: ${cartData.detailCart.size.toString()}"
        var chiTiet = ""
        cartData.detailCart.forEach { item->
         chiTiet += "Tên sản phẩm: ${item.product.title}, số lượng: ${item.detailCart.quantity}\n"
        }


        var sumPrice = "Tổng tiền: ${cartData.sumPrice}"
        content = "Bạn đã đặt hàng thành công đơn hàng: ${cartData.cart.id} vào lúc: ${time}\n ${soSanPham}\n ${chiTiet}\n ${sumPrice}"

        val newNotification = notification(
            id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
            status = false,
            title = "Bạn đã đặt hàng thành công",
            content = content
        )
        val updatedList = _notifications.value?.toMutableList() ?: mutableListOf()
        updatedList.add(newNotification)
        _notifications.value = updatedList
        Log.e("data notifi", "addNotificationOrder: "+_notifications.value, )
    }

    fun update(notificationId: Int) {
        _notifications.value = _notifications.value?.map { notification ->
            if (notification.id == notificationId) {
                notification.copy(status = true) // Đổi trạng thái thành true
            } else {
                notification
            }
        }
    }

}