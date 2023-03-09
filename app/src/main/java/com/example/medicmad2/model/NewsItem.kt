package com.example.medicmad2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/*
Описание: Модель карты новостей и акций
Дата создания: 09.03.2023 10:20
Автор: Георгий Хасанов
*/
@Keep
data class NewsItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("image")
    val image: String
)
