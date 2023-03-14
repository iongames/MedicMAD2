package com.example.medicmad2.model

import androidx.annotation.Keep

/*
Описание: Модель элемента корзины в заказе
Дата создания: 14.03.2023 12:40
Автор: Георгий Хасанов
*/
@Keep
data class OrderItem(
    val catalog_id: Int,
    val price: String
)
