package com.example.medicmad2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

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
