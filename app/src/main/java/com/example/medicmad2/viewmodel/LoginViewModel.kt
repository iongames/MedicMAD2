package com.example.medicmad2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicmad2.common.ApiService
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val responseCode = MutableLiveData<Int>()
    val token = MutableLiveData<String>()

    val message = MutableLiveData<String>()

    fun sendEmailCode(email: String) {
        responseCode.value = null
        message.value = null

        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val json = apiService.sendEmailCode(email)

                if (json.code() == 200) {
                    responseCode.value = json.code()
                } else {
                    message.value = json.body()!!.get("errors").toString()
                }
            } catch (e: Exception) {
                message.value = e.message
            }
        }
    }

    fun checkEmailCode(email: String, code: String) {
        responseCode.value = null
        message.value = null

        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val json = apiService.checkEmailCode(email, code)

                if (json.code() == 200) {
                    token.value = json.body()!!.get("token").toString()
                } else {
                    message.value = json.body()!!.get("errors").toString()
                }
            } catch (e: Exception) {
                message.value = e.message
            }
        }
    }
}