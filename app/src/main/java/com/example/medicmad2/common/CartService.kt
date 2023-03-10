package com.example.medicmad2.common

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.medicmad2.model.CartItem
import com.google.gson.Gson
import com.google.gson.JsonArray

/*
Описание: Класс хранения данных корзины в локальном хранлище
Дата создания: 10.03.2023 9:15
Автор: Георгий Хасанов
*/
class CartService {
    /*
    Описание: Метод получения данных из корзины
    Дата создания: 10.03.2023 9:15
    Автор: Георгий Хасанов
    */
    fun getCartData(sharedPreferences: SharedPreferences): MutableList<CartItem> {
        val cart = mutableStateListOf<CartItem>()

        val jsonCart: JsonArray = Gson().fromJson(sharedPreferences.getString("cart", "[]"), JsonArray::class.java)

        jsonCart.forEach { element ->
            val jsonObject = element.asJsonObject

            cart.add(
                CartItem(
                    id = jsonObject.get("id").asInt,
                    name = jsonObject.get("name").asString,
                    price = jsonObject.get("price").asString,
                    count = jsonObject.get("count").asInt
                )
            )
        }

        Log.d("JSON", "getCartData: $jsonCart")

        return cart
    }

    /*
    Описание: Метод сохранения данных корзины
    Дата создания: 10.03.2023 9:15
    Автор: Георгий Хасанов
    */
    fun saveCartData(sharedPreferences: SharedPreferences, cart: MutableList<CartItem>) {
        val jsonCart = Gson().toJson(cart)

        with(sharedPreferences.edit()) {
            putString("cart", jsonCart)
            apply()
        }
    }

    /*
    Описание: Метод добавления товара в корзину
    Дата создания: 10.03.2023 9:30
    Автор: Георгий Хасанов
    */
    fun addToCart(cartItem: CartItem, cart: MutableList<CartItem>): MutableList<CartItem> {
        val itemIndex = cart.indexOfFirst { it.id == cartItem.id }

        if (itemIndex != -1) {
            cart[itemIndex].count += 1
        } else {
            cart.add(
                CartItem(
                    cartItem.id,
                    cartItem.name,
                    cartItem.price,
                    1
                )
            )
        }

        return cart
    }

    /*
    Описание: Метод удаления товара из корзины
    Дата создания: 10.03.2023 9:30
    Автор: Георгий Хасанов
    */
    fun removeFromCart(cartItemIndex: Int, cart: MutableList<CartItem>): MutableList<CartItem> {
        if (cart[cartItemIndex].count > 1) {
            cart[cartItemIndex].count -= 1
        } else {
            cart.removeAt(cartItemIndex)
        }

        return cart
    }
}