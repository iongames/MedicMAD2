package com.example.medicmad2.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicmad2.common.ApiService
import com.example.medicmad2.model.Order
import com.example.medicmad2.model.OrderItem
import com.example.medicmad2.model.OrderPatient
import com.example.medicmad2.model.User
import kotlinx.coroutines.launch

class OrderViewModel: ViewModel() {
    val response = MutableLiveData<Int>()
    val message = MutableLiveData<String>()

    fun createOrder(token: String, address: String, date: String, phone: String, comment: String, patients: MutableList<User>) {
        response.value = null
        message.value = null

        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val orderPatients: MutableList<OrderPatient> = mutableStateListOf()

                for (p in patients.distinct()) {
                    val items: MutableList<OrderItem> = mutableStateListOf()

                    for (i in p.cart) {
                        items.add(
                            OrderItem(
                                i.id,
                                i.price
                            )
                        )
                    }

                    orderPatients.add(
                        OrderPatient(
                            "${p.lastname} ${p.firstname}",
                            items
                        )
                    )
                }

                val order = Order(
                    address,
                    date,
                    phone,
                    comment,
                    "",
                    orderPatients
                )

                val json = apiService.createOrder("Bearer ${token.replace("\"", "").trim()}", order)

                if (json.code() == 200) {
                    response.value = 200
                } else {
                    message.value = json.code().toString()
                }
            } catch (e: Exception) {
                message.value = e.message
            }
        }
    }
}