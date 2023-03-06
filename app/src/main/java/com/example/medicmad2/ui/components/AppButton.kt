package com.example.medicmad2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.medicmad2.ui.theme.secondaryColor

/*
Описание: Текстовая кнопка без фона
Дата создания: 06.03.2023 14:50
Автор: Георгий Хасанов
*/
@Composable
fun AppTextButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight = FontWeight.W600,
    color: Color = secondaryColor,
    onClick: () -> Unit
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        modifier = modifier.clickable { onClick() }
    )
}