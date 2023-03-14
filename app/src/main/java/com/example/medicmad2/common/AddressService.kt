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
<<<<<<< HEAD
Описание: Класс хранения данных адреса
Дата создания: 11.03.2023 15:40
=======
Описание: Класс хранения данных адресов пользователей
Дата создания: 14.03.2023 11:10
>>>>>>> Session-5
Автор: Георгий Хасанов
*/
class AddressService {
    /*
<<<<<<< HEAD
    Описание: Метод получения данных адреса
    Дата создания: 11.03.2023 15:45
=======
    Описание: Метод получения данных адресов из храниища
    Дата создания: 14.03.2023 11:10
>>>>>>> Session-5
    Автор: Георгий Хасанов
    */
    fun getAddressListData(sharedPreferences: SharedPreferences): MutableList<Address> {
        val addressList = mutableStateListOf<Address>()

<<<<<<< HEAD
        val jsonAddressList: JsonArray = Gson().fromJson(sharedPreferences.getString("addressList", "[]"), JsonArray::class.java)

        jsonAddressList.forEach { element ->
=======
        val jsonList: JsonArray = Gson().fromJson(sharedPreferences.getString("addressList", "[]"), JsonArray::class.java)

        jsonList.forEach { element ->
>>>>>>> Session-5
            val jsonObject = element.asJsonObject

            addressList.add(
                Address(
<<<<<<< HEAD
                    jsonObject.get("addressText").asString,
                    jsonObject.get("lonText").asString,
                    jsonObject.get("latText").asString,
                    jsonObject.get("altText").asString,
                    jsonObject.get("flatText").asString,
                    jsonObject.get("enterText").asString,
                    jsonObject.get("floorText").asString,
                    jsonObject.get("enterCodeText").asString,
=======
                    addressText = jsonObject.get("addressText").asString,
                    lon = jsonObject.get("lon").asString,
                    lat = jsonObject.get("lat").asString,
                    alt = jsonObject.get("alt").asString,
                    flat = jsonObject.get("flat").asString,
                    enter = jsonObject.get("enter").asString,
                    floor = jsonObject.get("floor").asString,
                    enterCode = jsonObject.get("enterCode").asString,
                    addressName = jsonObject.get("addressName").asString
>>>>>>> Session-5
                )
            )
        }

        return addressList
    }

    /*
<<<<<<< HEAD
    Описание: Метод сохранения данных адреса
    Дата создания: 11.03.2023 15:45
    Автор: Георгий Хасанов
    */
    fun saveAddressData(sharedPreferences: SharedPreferences, addressList: MutableList<Address>) {
        val jsonAddressList = Gson().toJson(addressList)

        with(sharedPreferences.edit()) {
            putString("addressList", jsonAddressList)
=======
    Описание: Метод сохранения данных адресов
    Дата создания: 14.03.2023 11:12
    Автор: Георгий Хасанов
    */
    fun saveAddressListData(sharedPreferences: SharedPreferences, addressList: MutableList<Address>) {
        val jsonList = Gson().toJson(addressList)

        with(sharedPreferences.edit()) {
            putString("addressList", jsonList)
>>>>>>> Session-5
            apply()
        }
    }

    /*
    Описание: Метод добавления адреса в список
<<<<<<< HEAD
    Дата создания: 11.03.2023 15:45
=======
    Дата создания: 14.03.2023 11:12
>>>>>>> Session-5
    Автор: Георгий Хасанов
    */
    fun addAddressToList(address: Address, addressList: MutableList<Address>): MutableList<Address> {
        addressList.add(address)

        return addressList
    }
}