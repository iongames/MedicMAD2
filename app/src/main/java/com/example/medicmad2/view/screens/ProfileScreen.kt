package com.example.medicmad2.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.medicmad2.R

/*
Описание: Экран профиля пользователя
Дата создания: 09.03.2023 9:40
Автор: Георгий Хасанов
*/
@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_medicmad_foreground),
            contentDescription = "",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}