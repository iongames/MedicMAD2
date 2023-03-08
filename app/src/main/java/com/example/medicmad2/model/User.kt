package com.example.medicmad2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class User(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("firstname")
    var firstname: String,
    @SerializedName("lastname")
    var lastname: String,
    @SerializedName("middlename")
    var middlename: String,
    @SerializedName("bith")
    var bith: String,
    @SerializedName("pol")
    var pol: String,
    @SerializedName("image")
    var image: String,
)
