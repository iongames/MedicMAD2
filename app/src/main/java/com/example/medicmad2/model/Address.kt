package com.example.medicmad2.model

import androidx.annotation.Keep

<<<<<<< HEAD
@Keep
data class Address(
    val addressText: String,
    val lonText: String,
    val latText: String,
    val altText: String,
    val flatText: String,
    val enterText: String,
    val floorText: String,
    val enterCodeText: String
=======
/*
Описание: Модель адреса клиента
Дата создания: 14.03.2023 11:05
Автор: Георгий Хасанов
*/
@Keep
data class Address(
    val addressText: String,
    val lon: String,
    val lat: String,
    val alt: String,
    val flat: String,
    val enter: String,
    val floor: String,
    val enterCode: String,
    val addressName: String = ""
>>>>>>> Session-5
)
