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
Описание: Класс хранения данных адреса
Дата создания: 11.03.2023 15:40
Автор: Георгий Хасанов
*/
class AddressService {
    /*
    Описание: Метод получения данных адреса
    Дата создания: 11.03.2023 15:45
    Автор: Георгий Хасанов
    */
    fun getAddressListData(sharedPreferences: SharedPreferences): MutableList<Address> {
        val addressList = mutableStateListOf<Address>()

        val jsonAddressList: JsonArray = Gson().fromJson(sharedPreferences.getString("addressList", "[]"), JsonArray::class.java)

        jsonAddressList.forEach { element ->
            val jsonObject = element.asJsonObject

            addressList.add(
                Address(
                    jsonObject.get("addressText").asString,
                    jsonObject.get("lonText").asString,
                    jsonObject.get("latText").asString,
                    jsonObject.get("altText").asString,
                    jsonObject.get("flatText").asString,
                    jsonObject.get("enterText").asString,
                    jsonObject.get("floorText").asString,
                    jsonObject.get("enterCodeText").asString,
                )
            )
        }

        return addressList
    }

    /*
    Описание: Метод сохранения данных адреса
    Дата создания: 11.03.2023 15:45
    Автор: Георгий Хасанов
    */
    fun saveAddressData(sharedPreferences: SharedPreferences, addressList: MutableList<Address>) {
        val jsonAddressList = Gson().toJson(addressList)

        with(sharedPreferences.edit()) {
            putString("addressList", jsonAddressList)
            apply()
        }
    }

    /*
    Описание: Метод добавления адреса в список
    Дата создания: 11.03.2023 15:45
    Автор: Георгий Хасанов
    */
    fun addAddressToList(address: Address, addressList: MutableList<Address>): MutableList<Address> {
        addressList.add(address)

        return addressList
    }
}