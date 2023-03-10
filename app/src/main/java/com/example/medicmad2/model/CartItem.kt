package com.example.medicmad2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/*
Описание: Модель элемента корзины товаров
Дата создания: 10.03.2023 9:15
Автор: Георгий Хасанов
*/
@Keep
data class CartItem(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("price")
    var price: String,
    @SerializedName("count")
    var count: Int,
)
