package com.example.medicmad2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.model.CatalogItem
import com.example.medicmad2.R
import com.example.medicmad2.ui.theme.descriptionColor

/*
Описание: Окно Карточка товара с описанием
Дата создания: 10.03.2023 8:55
Автор: Георгий Хасанов
*/
@Composable
fun AnalysisCard(
    catalogItem: CatalogItem,
    addToCart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                catalogItem.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_dismiss_button),
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "Описание",
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = descriptionColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            catalogItem.description,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Описание",
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = descriptionColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            catalogItem.preparation,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(60.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    "Результаты через:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    color = descriptionColor
                )
                Text(
                    catalogItem.time_result,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            }
            Spacer(modifier = Modifier.width(40.dp))
            Column {
                Text(
                    "Биоматериал:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    color = descriptionColor
                )
                Text(
                    catalogItem.bio,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            text = "Добавить за ${catalogItem.price} ₽",
            fontSize = 17.sp,
            fontWeight = FontWeight.W600,
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            addToCart()
        }
    }
}