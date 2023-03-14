package com.example.medicmad2.ui.components

import android.app.DatePickerDialog
import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.R
import com.example.medicmad2.model.Address
import com.example.medicmad2.model.User
import com.example.medicmad2.ui.theme.*
import com.example.medicmad2.view.CreateCardActivity
import kotlinx.coroutines.launch
import java.util.Calendar

/*
Описание: Окно с выбором адреса клиента
Дата создания: 13.03.2023 11:45
Автор: Георгий Хасанов
*/
@Composable
fun AddressBottomSheet(
    onIconClick: () -> Unit,
    onAddressSelect: (Address) -> Unit,
    onAddressSave: (Address) -> Unit
) {
    var address by rememberSaveable { mutableStateOf("") }

    var lat by rememberSaveable { mutableStateOf("") }
    var lon by rememberSaveable { mutableStateOf("") }
    var alt by rememberSaveable { mutableStateOf("") }

    var flat by rememberSaveable { mutableStateOf("") }
    var enter by rememberSaveable { mutableStateOf("") }
    var floor by rememberSaveable { mutableStateOf("") }

    var enterCode by rememberSaveable { mutableStateOf("") }

    var addressSaveEnabled by rememberSaveable { mutableStateOf(false) }
    var addressName by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 24.dp)
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
                tint = selectedStrokeColor,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onIconClick()
                    }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OrderTextField(
            title = "Ваш адрес",
            placeholder = { Text(text = "") },
            value = address,
            onValueChange = { address = it },
            readOnly = false
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AddressTextField(
                title = "Долгота",
                placeholder = { Text(text = "") },
                value = lon,
                onValueChange = { lon = it },
                readOnly = true,
                modifier = Modifier.weight(4f)
            )
            Spacer(modifier = Modifier.width(12.5.dp))
            AddressTextField(
                title = "Широта",
                placeholder = { Text(text = "") },
                value = lat,
                onValueChange = { lat = it },
                readOnly = true,
                modifier = Modifier.weight(4f)
            )
            Spacer(modifier = Modifier.width(12.5.dp))
            AddressTextField(
                title = "Высота",
                placeholder = { Text(text = "") },
                value = alt,
                onValueChange = { alt = it },
                readOnly = true,
                modifier = Modifier.weight(3f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AddressTextField(
                title = "Квартира",
                placeholder = { Text(text = "") },
                value = flat,
                onValueChange = { flat = it },
                readOnly = false,
                modifier = Modifier.weight(4f)
            )
            Spacer(modifier = Modifier.width(17.5.dp))
            AddressTextField(
                title = "Подъезд",
                placeholder = { Text(text = "") },
                value = enter,
                onValueChange = { enter = it },
                readOnly = false,
                modifier = Modifier.weight(4f)
            )
            Spacer(modifier = Modifier.width(17.5.dp))
            AddressTextField(
                title = "Этаж",
                placeholder = { Text(text = "") },
                value = floor,
                onValueChange = { floor = it },
                readOnly = false,
                modifier = Modifier.weight(4f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OrderTextField(
            title = "Домофон",
            placeholder = { Text(text = "") },
            value = enterCode,
            onValueChange = { enterCode = it },
            readOnly = false
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Сохранить этот адрес?",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
            Switch(
                checked = addressSaveEnabled,
                onCheckedChange = { addressSaveEnabled = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.White,
                    checkedTrackColor = primaryColor,
                    uncheckedTrackColor = strokeColor
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (addressSaveEnabled) {
            AppTextField(
                value = addressName,
                onValueChange = { addressName = it },
                contentPadding = PaddingValues(16.dp),
                placeholder = { Text("Название: например дом, работа", fontSize = 15.sp, color = descriptionColor) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        AppButton(
            text = "Подтвердить",
            fontSize = 17.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.fillMaxWidth()
        ) {
            val addressItem = Address(
                address,
                lon, lat, alt,
                flat, enter, floor,
                enterCode,
                addressName
            )

            onAddressSelect(addressItem)

            if (addressSaveEnabled) {
                onAddressSave(addressItem)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/*
Описание: Окно с выбором времени анализа
Дата создания: 13.03.2023 11:45
Автор: Георгий Хасанов
*/
@Composable
fun TimeBottomSheet(
    onIconClick: () -> Unit,
    onTimeSelect: (String, String) -> Unit
) {
    val mContext = LocalContext.current
    var dateText by rememberSaveable { mutableStateOf("") }

    val timeArray = listOf(
        "10:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "18:00",
        "19:00"
    )

    var selectedTime by rememberSaveable { mutableStateOf("") }
    var selectedTrueTime by rememberSaveable { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    val mYear = calendar.get(Calendar.YEAR)
    val mMonth = calendar.get(Calendar.MONTH)
    val mDay = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        mContext,
        { _, year: Int, month: Int, day: Int ->
            var date = "$day $month"
            selectedTrueTime = "$day $month $year"

            when (month) {
                0 -> { date = date.replace(" 0", " января") }
                1 -> { date = date.replace(" $month", " февраля") }
                2 -> { date = date.replace(" $month", " марта") }
                3 -> { date = date.replace(" $month", " апреля") }
                4 -> { date = date.replace(" $month", " мая") }
                5 -> { date = date.replace(" $month", " июня") }
                6 -> { date = date.replace(" $month", " июля") }
                7 -> { date = date.replace(" $month", " августа") }
                8 -> { date = date.replace(" $month", " сентября") }
                9 -> { date = date.replace(" $month", " октября") }
                10 -> { date = date.replace(" $month", " ноября") }
                11 -> { date = date.replace(" $month", " декабря") }
            }

            dateText = date
        }, mYear, mMonth, mDay
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 24.dp)
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
        Text(
            "Выберите дату",
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            color = descriptionColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            value = dateText,
            onValueChange = {},
            contentPadding = PaddingValues(16.dp),
            placeholder = { Text("") },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dropdown),
                    contentDescription = "",
                    tint = secondaryTextColor,
                    modifier = Modifier.clickable {
                        datePickerDialog.show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "Выберите время",
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            color = descriptionColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            content = {
                items(timeArray) {
                    Card(
                        elevation = 0.dp,
                        backgroundColor = if (selectedTime == it) primaryColor else inputColor,
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 16.dp)
                            .clickable {
                                selectedTime = it
                            }
                    ) {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            color = if (selectedTime == it) Color.White else secondaryTextColor,
                            modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        AppButton(
            text = "Подтвердить",
            fontSize = 17.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.fillMaxWidth()
        ) {
            onTimeSelect("$dateText $selectedTime", "$selectedTrueTime $selectedTime")
        }
        Spacer(modifier = Modifier.height(32.dp))
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
    onIconClick: () -> Unit,
    onUserSelect: (User) -> Unit
) {
    val mContext = LocalContext.current

    var selected: User? by remember { mutableStateOf(null) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 24.dp)
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
                elevation = 0.dp,
                backgroundColor = if (selected == user) primaryColor else inputColor,
                modifier = Modifier.clickable {
                    selected = user
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
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
            val intent = Intent(mContext, CreateCardActivity::class.java)
            mContext.startActivity(intent)
        }
        Spacer(modifier = Modifier.height(40.dp))
        AppButton(
            text = "Подтвердить",
            fontSize = 17.sp,
            fontWeight = FontWeight.W600,
            enabled = selected != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            onUserSelect(selected!!)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}