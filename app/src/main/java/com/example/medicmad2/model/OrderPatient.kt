package com.example.medicmad2.model

import androidx.annotation.Keep

/*
Описание: Модель клиента в заказе
Дата создания: 14.03.2023 12:40
Автор: Георгий Хасанов
*/
@Keep
data class OrderPatient(
    val name: String,
    val items: MutableList<OrderItem>,
)
