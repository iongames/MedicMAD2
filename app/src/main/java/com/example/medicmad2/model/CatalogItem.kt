package com.example.medicmad2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/*
Описание: Модель карты каталога анализов
Дата создания: 09.03.2023 10:30
Автор: Георгий Хасанов
*/
@Keep
data class CatalogItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("time_result")
    val time_result: String,
    @SerializedName("preparation")
    val preparation: String,
    @SerializedName("bio")
    val bio: String
)
