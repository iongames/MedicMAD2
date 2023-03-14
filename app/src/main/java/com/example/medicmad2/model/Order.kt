package com.example.medicmad2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/*
Описание: Модель заказа
Дата создания: 14.03.2023 13:20
Автор: Георгий Хасанов
*/
@Keep
data class Order(
    @SerializedName("address")
    val address: String,
    @SerializedName("date_time")
    val date_time: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("audio_comment")
    val audio_comment: String,
    @SerializedName("patients")
    val patients: MutableList<OrderPatient>
)
