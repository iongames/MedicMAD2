package com.example.medicmad2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.R
import com.example.medicmad2.ui.theme.*

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

/*
Описание: Кнопка приложения с фоном
Дата создания: 08.03.2023 12:30
Автор: Георгий Хасанов
*/
@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 17.sp,
    fontWeight: FontWeight = FontWeight.W600,
    color: Color = Color.White,
    borderStroke: BorderStroke = BorderStroke(0.dp, strokeColor),
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary, disabledBackgroundColor = primaryDisabledColor),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.elevation(0.dp),
        colors = colors,
        border = borderStroke,
        enabled = enabled,
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color
        )
    }
}

/*
Описание: Кнопка перехода на предыдущий экран
Дата создания: 08.03.2023 12:50
Автор: Георгий Хасанов
*/
@Composable
fun AppBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        backgroundColor = inputColor,
        shape = RoundedCornerShape(8.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "",
            tint = descriptionColor,
            modifier = Modifier.padding(6.dp)
        )
    }
}