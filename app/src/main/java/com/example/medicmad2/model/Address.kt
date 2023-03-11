package com.example.medicmad2.model

import androidx.annotation.Keep

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
)
