package com.example.medicmad2.ui.components

import android.graphics.Paint.Align
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.R
import com.example.medicmad2.model.CartItem
import com.example.medicmad2.model.CatalogItem
import com.example.medicmad2.model.NewsItem
import com.example.medicmad2.ui.theme.descriptionColor
import com.example.medicmad2.ui.theme.inputColor
import com.example.medicmad2.ui.theme.primaryColor
import com.example.medicmad2.ui.theme.secondaryTextColor
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

/*
Описание: Карточка блока "Акции и новости"
Дата создания: 09.03.2023 9:40
Автор: Георгий Хасанов
*/
@Composable
fun AppNewsItemCard(
    newsItem: NewsItem
) {
    Box(
        modifier = Modifier
            .size(width = 280.dp, height = 180.dp)
            .clip(MaterialTheme.shapes.large)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF76B3FF),
                        Color(0xFFCDE3FF)
                    ),
                    Offset(0f, 0f),
                    Offset(560f, 180f)
                )
            )
    )  {
        GlideImage(
            imageModel = newsItem.image,
            imageOptions = ImageOptions(alignment = Alignment.CenterEnd, contentScale = ContentScale.FillHeight),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 10.dp)
        )
        Text(
            newsItem.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.W800,
            softWrap = true,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(0.75f)
                .padding(16.dp)
        )
        Column(modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(start = 16.dp, bottom = 12.dp)) {
            Text(
                newsItem.description,
                fontSize = 14.sp,
                color = Color.White
            )
            Text(
                "${newsItem.price} ₽",
                fontSize = 20.sp,
                fontWeight = FontWeight.W800,
                color = Color.White
            )
        }
    }
}

/*
Описание: Карточка блока "Каталог аналзиов"
Дата создания: 09.03.2023 10:00
Автор: Георгий Хасанов
*/
@Composable
fun AppCatalogItemCard(
    catalogItem: CatalogItem,
    itemInCart: Boolean,
    onButtonClick: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, MaterialTheme.shapes.large, ambientColor = Color(0x1A000000))
            .height(160.dp)
            .clip(MaterialTheme.shapes.large)
            .background(Color.White)
            .border(1.dp, Color(0xFFF4F4F4), MaterialTheme.shapes.large)
            .clickable { onClick() }
    )  {
        Text(
            catalogItem.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            softWrap = true,
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = catalogItem.time_result,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    color = descriptionColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${catalogItem.price} ₽",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black
                )
            }
            if (itemInCart) {
                AppButton(
                    text = "Убрать",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    color = primaryColor,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ),
                    borderStroke = BorderStroke(1.dp, primaryColor),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    onClick = onButtonClick
                )
            } else {
                AppButton(
                    text = "Добавить",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    onClick = onButtonClick
                )
            }

        }
    }
}

/*
Описание: Карточка с названием категории
Дата создания: 09.03.2023 10:00
Автор: Георгий Хасанов
*/
@Composable
fun AppCategoryCard(
    title: String,
    selectedItem: String,
    onClick: () -> Unit
) {
    Card (
        elevation = 0.dp,
        backgroundColor = if (selectedItem == title) { primaryColor } else { inputColor },
        modifier = Modifier.clickable { onClick() }
    ){
        Text(
            title,
            fontSize = 15.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            color = if (selectedItem == title) { Color.White } else { secondaryTextColor }
        )
    }
}

/*
Описание: Карточка товара корзины
Дата создания: 09.03.2023 10:00
Автор: Георгий Хасанов
*/
@Composable
fun AppCatalogItemCard(
    cartItem: CartItem,
    onItemDelete: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, MaterialTheme.shapes.large, ambientColor = Color(0x1A000000))
            .height(160.dp)
            .clip(MaterialTheme.shapes.large)
            .background(Color.White)
            .border(1.dp, Color(0xFFF4F4F4), MaterialTheme.shapes.large)
            .clickable { onClick() }
    )  {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                cartItem.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                softWrap = true,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(16.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {

                    }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "${cartItem.price} ₽",
                fontSize = 17.sp,
                fontWeight = FontWeight.W500
            )
            Spacer(modifier = Modifier.height(4.dp))

        }
    }
}