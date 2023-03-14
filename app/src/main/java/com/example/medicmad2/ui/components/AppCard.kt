package com.example.medicmad2.ui.components

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.R
import com.example.medicmad2.model.CartItem
import com.example.medicmad2.model.CatalogItem
import com.example.medicmad2.model.NewsItem
import com.example.medicmad2.model.User
import com.example.medicmad2.ui.theme.*
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
            .shadow(2.dp, MaterialTheme.shapes.large, ambientColor = Color(0x1A000000))
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
fun AppCartItemCard(
    cartItem: CartItem,
    onItemAdd: () -> Unit,
    onItemDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, MaterialTheme.shapes.large, ambientColor = Color(0x1A000000))
            .height(160.dp)
            .clip(MaterialTheme.shapes.large)
            .background(Color.White)
            .border(1.dp, Color(0xFFF4F4F4), MaterialTheme.shapes.large)
    )  {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                cartItem.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                softWrap = true,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "",
                tint = secondaryTextColor,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onItemDelete()
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${cartItem.count} пациент",
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Card(
                    backgroundColor = inputColor,
                    elevation = 0.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minus),
                            contentDescription = "",
                            tint = if (cartItem.count > 1) descriptionColor else selectedStrokeColor,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(enabled = cartItem.count > 1) {
                                    onItemDelete()
                                }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Spacer(modifier = Modifier
                            .size(width = 1.dp, height = 16.dp)
                            .background(strokeColor)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "",
                            tint = descriptionColor,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    onItemAdd()
                                }
                        )
                    }
                }
            }
        }
    }
}

/*
Описание: Карточка поиска товара
Дата создания: 10.03.2023 12:00
Автор: Георгий Хасанов
*/
@Composable
fun AppSearchItemCard(
    catalogItem: CatalogItem,
    searchText: String,
    onClick: () -> Unit
) {
    val text = "${catalogItem.name} (${catalogItem.bio})".split(searchText, ignoreCase = true)

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .clickable { onClick() }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text(
                buildAnnotatedString {
                    for ((index, t) in text.withIndex()) {
                        append(t)

                        if (index != text.size - 1) {
                            withStyle(SpanStyle(color = primaryColor)) {
                                append(searchText.lowercase())
                            }
                        }
                    }
                },
                color = Color.Black,
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth(0.6f)
            )
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    "${catalogItem.price} ₽",
                    fontSize = 17.sp,
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    "${catalogItem.time_result} ₽",
                    fontSize = 14.sp,
                    color = descriptionColor
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(dividerColor))
    }
}

/*
Описание: Карточка поиска товара
Дата создания: 10.03.2023 12:00
Автор: Георгий Хасанов
*/
@Composable
fun OrderUserCard(
    user: User,
    cart: MutableList<CartItem>,
    summaryCart: MutableList<CartItem>,
    onItemAdd: (CartItem) -> Unit,
    onItemDelete: (CartItem) -> Unit,
    onUserDelete: (User) -> Unit,
    onUserChange: (User) -> Unit,
) {
    //val patientCart: MutableList<CartItem> = remember {
    //    mutableStateListOf()
    //}

    Card(
        elevation = 0.dp,
        backgroundColor = Color.White,
        border = BorderStroke(1.dp, strokeColor)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AppTextField(
                    value = "${user.lastname} ${user.firstname}",
                    onValueChange = {},
                    contentPadding = PaddingValues(16.dp),
                    placeholder = { Text("") },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_male),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dropdown),
                            contentDescription = "",
                            tint = secondaryTextColor,
                            modifier = Modifier.clickable {
                                onUserChange(user)
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(0.75f),
                    readOnly = true
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "",
                    tint = selectedStrokeColor,
                    modifier = Modifier.clickable {
                        onUserDelete(user)
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            for (item in cart.distinct()) {
                var checked by rememberSaveable { mutableStateOf(true) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(0.75f)
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = {
                                checked = it

                                if (it) {
                                    val selectedIndex = summaryCart.indexOfFirst { indItem -> indItem.id == item.id }

                                    if (selectedIndex != -1) {
                                        val last = summaryCart[selectedIndex]
                                        summaryCart.removeAt(selectedIndex)
                                        summaryCart.add(
                                            CartItem(
                                                last.id,
                                                last.name,
                                                last.price,
                                                last.count + 1
                                            )
                                        )
                                    }
                                } else {
                                    val selectedIndex = summaryCart.indexOfFirst { indItem -> indItem.id == item.id }

                                    if (selectedIndex != -1) {
                                        val last = summaryCart[selectedIndex]
                                        summaryCart.removeAt(selectedIndex)
                                        summaryCart.add(
                                            CartItem(
                                                last.id,
                                                last.name,
                                                last.price,
                                                last.count - 1
                                            )
                                        )
                                    }
                                }
                            },
                            colors = CheckboxDefaults.colors(checkedColor = primaryColor, uncheckedColor = inputColor)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = item.name,
                            color = if (checked) Color.Black else descriptionColor,
                            fontSize = 12.sp,
                        )
                    }
                    Text(
                        text = "${item.price} ₽",
                        color = if (checked) Color.Black else descriptionColor,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}