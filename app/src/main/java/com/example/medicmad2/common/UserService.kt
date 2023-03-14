package com.example.medicmad2.common

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.medicmad2.model.CartItem
import com.example.medicmad2.model.User
import com.google.gson.Gson
import com.google.gson.JsonArray

/*
Описание: Класс хранения данных пользователей в локальном хранлище
Дата создания: 10.03.2023 12:55
Автор: Георгий Хасанов
*/
class UserService {
    /*
    Описание: Метод получения данных из корзины
    Дата создания: 10.03.2023 12:55
    Автор: Георгий Хасанов
    */
    fun getUserListData(sharedPreferences: SharedPreferences): MutableList<User> {
        val userList = mutableStateListOf<User>()

        val jsonUserList: JsonArray = Gson().fromJson(sharedPreferences.getString("userList", "[]"), JsonArray::class.java)

        jsonUserList.forEach { element ->
            val jsonObject = element.asJsonObject
            val jsonCart = jsonObject.get("cart").asJsonArray

            val cart: MutableList<CartItem> = mutableStateListOf()

            for (item in jsonCart) {
                cart.add(
                    CartItem(
                        id = item.asJsonObject.get("id").asInt,
                        name = item.asJsonObject.get("name").asString,
                        price = item.asJsonObject.get("price").asString,
                        count = item.asJsonObject.get("count").asInt
                    )
                )
            }

            userList.add(
                User(
                    id = jsonObject.get("id").asInt,
                    firstname = jsonObject.get("firstname").asString,
                    lastname = jsonObject.get("lastname").asString,
                    middlename = jsonObject.get("middlename").asString,
                    bith = jsonObject.get("bith").asString,
                    pol = jsonObject.get("pol").asString,
                    image = jsonObject.get("image").asString,
                    cart = cart
                )
            )
        }

        return userList
    }

    /*
    Описание: Метод сохранения данных пользователей
    Дата создания: 10.03.2023 12:55
    Автор: Георгий Хасанов
    */
    fun saveUserListData(sharedPreferences: SharedPreferences, userList: MutableList<User>) {
        val jsonUserList = Gson().toJson(userList)

        with(sharedPreferences.edit()) {
            putString("userList", jsonUserList)
            apply()
        }
    }

    /*
    Описание: Метод добавления пользователя в список
    Дата создания: 10.03.2023 12:55
    Автор: Георгий Хасанов
    */
    fun addUserToList(user: User, userList: MutableList<User>): MutableList<User> {
        userList.add(user)

        return userList
    }
}