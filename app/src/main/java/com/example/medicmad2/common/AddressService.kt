package com.example.medicmad2.common

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.medicmad2.model.Address
import com.example.medicmad2.model.CartItem
import com.example.medicmad2.model.User
import com.google.gson.Gson
import com.google.gson.JsonArray

/*
Описание: Класс хранения данных адресов пользователей
Дата создания: 14.03.2023 11:10
Автор: Георгий Хасанов
*/
class AddressService {
    /*
    Описание: Метод получения данных адресов из храниища
    Дата создания: 14.03.2023 11:10
    Автор: Георгий Хасанов
    */
    fun getAddressListData(sharedPreferences: SharedPreferences): MutableList<Address> {
        val addressList = mutableStateListOf<Address>()

        val jsonList: JsonArray = Gson().fromJson(sharedPreferences.getString("addressList", "[]"), JsonArray::class.java)

        jsonList.forEach { element ->
            val jsonObject = element.asJsonObject

            addressList.add(
                Address(
                    addressText = jsonObject.get("addressText").asString,
                    lon = jsonObject.get("lon").asString,
                    lat = jsonObject.get("lat").asString,
                    alt = jsonObject.get("alt").asString,
                    flat = jsonObject.get("flat").asString,
                    enter = jsonObject.get("enter").asString,
                    floor = jsonObject.get("floor").asString,
                    enterCode = jsonObject.get("enterCode").asString,
                    addressName = jsonObject.get("addressName").asString
                )
            )
        }

        return addressList
    }

    /*
    Описание: Метод сохранения данных адресов
    Дата создания: 14.03.2023 11:12
    Автор: Георгий Хасанов
    */
    fun saveAddressListData(sharedPreferences: SharedPreferences, addressList: MutableList<Address>) {
        val jsonList = Gson().toJson(addressList)

        with(sharedPreferences.edit()) {
            putString("addressList", jsonList)
            apply()
        }
    }

    /*
    Описание: Метод добавления адреса в список
    Дата создания: 14.03.2023 11:12
    Автор: Георгий Хасанов
    */
    fun addAddressToList(address: Address, addressList: MutableList<Address>): MutableList<Address> {
        addressList.add(address)

        return addressList
    }
}