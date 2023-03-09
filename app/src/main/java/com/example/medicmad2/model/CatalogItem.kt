package com.example.medicmad2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

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
