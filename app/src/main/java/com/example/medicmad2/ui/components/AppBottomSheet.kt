package com.example.medicmad2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.R
import com.example.medicmad2.model.User
import com.example.medicmad2.ui.theme.inputColor
import com.example.medicmad2.ui.theme.primaryColor
import com.example.medicmad2.ui.theme.secondaryTextColor

/*
Описание: Окно с выбором адреса клиента
Дата создания: 13.03.2023 11:45
Автор: Георгий Хасанов
*/
@Composable
fun AddressBottomSheet(
    onIconClick: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Адрес сдачи анализов",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_map),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0x1F818C99))
                    .clickable {
                        onIconClick()
                    }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

/*
Описание: Окно с выбором времени анализа
Дата создания: 13.03.2023 11:45
Автор: Георгий Хасанов
*/
@Composable
fun TimeBottomSheet(
    onIconClick: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Дата и время",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0x1F818C99))
                    .clickable {
                        onIconClick()
                    }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

/*
Описание: Окно с добавлением клиентов в заказ
Дата создания: 13.03.2023 11:45
Автор: Георгий Хасанов
*/
@Composable
fun PatientBottomSheet(
    userList: MutableList<User>,
    onIconClick: () -> Unit
) {
    var selected: User? by remember { mutableStateOf(null) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Выбор пациента",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0x1F818C99))
                    .clickable {
                        onIconClick()
                    }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        for (user in userList.distinct()) {
            Card(
                backgroundColor = if (selected == user) primaryColor else inputColor
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = if (user.pol == "Мужской") R.drawable.ic_male else R.drawable.ic_female),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "${user.lastname} ${user.firstname}",
                        fontSize = 14.sp,
                        color = if (selected == user) Color.White else Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        AppButton(
            text = "Добавить еще пациента",
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            ),
            borderStroke = BorderStroke(1.dp, primaryColor),
            color = primaryColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.fillMaxWidth()
        ) {

        }
    }
}