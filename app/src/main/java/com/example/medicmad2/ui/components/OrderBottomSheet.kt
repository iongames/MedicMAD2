package com.example.medicmad2.ui.components

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import java.text.SimpleDateFormat

/*
Описание: Окно выбора адреса сдачи анализов
Дата создания: 11.03.2023 14:50
Автор: Георгий Хасанов
*/
@Composable
fun AddressBottomSheet(
    onChanged: (Address) -> Unit,
    onAddressSave: (Address) -> Unit
) {
    var addressText by rememberSaveable { mutableStateOf("") }
    var lonText by rememberSaveable { mutableStateOf("") }
    var latText by rememberSaveable { mutableStateOf("") }
    var altText by rememberSaveable { mutableStateOf("") }
    var flatText by rememberSaveable { mutableStateOf("") }
    var enterText by rememberSaveable { mutableStateOf("") }
    var floorText by rememberSaveable { mutableStateOf("") }
    var enterCodeText by rememberSaveable { mutableStateOf("") }

    var addressSaveText by rememberSaveable { mutableStateOf("") }

    var checked by rememberSaveable { mutableStateOf(false) }

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
                "Адрес сдачи анализов",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                softWrap = true,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_map),
                contentDescription = "",
                tint = selectedStrokeColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Ваш адрес",
            fontSize = 14.sp,
            color = secondaryTextColor
        )
        Spacer(modifier = Modifier.height(4.dp))
        AppTextField(
            value = addressText,
            onValueChange = { addressText = it},
            placeholder = { Text("Введите ваш адрес", fontSize = 15.sp) },
            contentPadding = PaddingValues(14.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(4f)
            ) {
                Text(
                    "Долгота",
                    fontSize = 14.sp,
                    color = secondaryTextColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                AppTextField(
                    value = lonText,
                    onValueChange = { lonText = it},
                    placeholder = { Text("", fontSize = 15.sp) },
                    contentPadding = PaddingValues(14.dp),
                    readOnly = true
                )
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column(
                modifier = Modifier
                    .weight(4f)
            ) {
                Text(
                    "Широта",
                    fontSize = 14.sp,
                    color = secondaryTextColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                AppTextField(
                    value = latText,
                    onValueChange = { latText = it},
                    placeholder = { Text("", fontSize = 15.sp) },
                    contentPadding = PaddingValues(14.dp),
                    readOnly = true
                )
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    "Высота",
                    fontSize = 14.sp,
                    color = secondaryTextColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                AppTextField(
                    value = altText,
                    onValueChange = { altText = it},
                    placeholder = { Text("", fontSize = 15.sp) },
                    contentPadding = PaddingValues(14.dp),
                    readOnly = true
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    "Квартира",
                    fontSize = 14.sp,
                    color = secondaryTextColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                AppTextField(
                    value = flatText,
                    onValueChange = { flatText = it},
                    placeholder = { Text("", fontSize = 15.sp) },
                    contentPadding = PaddingValues(14.dp)
                )
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    "Подъезд",
                    fontSize = 14.sp,
                    color = secondaryTextColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                AppTextField(
                    value = enterText,
                    onValueChange = { enterText = it},
                    placeholder = { Text("", fontSize = 15.sp) },
                    contentPadding = PaddingValues(14.dp)
                )
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    "Этаж",
                    fontSize = 14.sp,
                    color = secondaryTextColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                AppTextField(
                    value = floorText,
                    onValueChange = { floorText = it},
                    placeholder = { Text("", fontSize = 15.sp) },
                    contentPadding = PaddingValues(14.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Домофон",
            fontSize = 14.sp,
            color = secondaryTextColor,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        AppTextField(
            value = enterCodeText,
            onValueChange = { enterCodeText = it},
            placeholder = { Text("", fontSize = 15.sp) },
            contentPadding = PaddingValues(14.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Сохранить этот адрес?",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Switch(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.White,
                    checkedTrackColor = primaryColor,
                    uncheckedTrackColor = strokeColor
                )
            )
        }
        if (checked) {
            Spacer(modifier = Modifier.height(13.dp))
            AppTextField(
                value = addressSaveText,
                onValueChange = { addressSaveText = it},
                placeholder = { Text("", fontSize = 15.sp) },
                contentPadding = PaddingValues(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        AppButton(
            text = "Подтвердить",
            fontSize = 17.sp,
            fontWeight = FontWeight.W600
        ) {
            onChanged(Address(
                addressText,
                lonText,
                latText,
                altText,
                flatText,
                enterText,
                floorText,
                enterCodeText,
            ))

            if (checked) {
                onAddressSave(Address(
                    addressText,
                    lonText,
                    latText,
                    altText,
                    flatText,
                    enterText,
                    floorText,
                    enterCodeText,
                ))
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/*
Описание: Окно выбора времени анализов
Дата создания: 11.03.2023 15:10
Автор: Георгий Хасанов
*/
@Composable
fun TimeBottomSheet(
    onClose: () -> Unit,
    onChanged: (String) -> Unit
) {
    val mContext = LocalContext.current
    var timeText by rememberSaveable { mutableStateOf("") }

    var timeArray = listOf(
        "10:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "18:00",
        "19:00"
    )

    var selectedTime by rememberSaveable { mutableStateOf(0) }

    val calendar = Calendar.getInstance()

    val mYear = calendar.get(Calendar.YEAR)
    val mMonth = calendar.get(Calendar.MONTH)
    val mDay = calendar.get(Calendar.DAY_OF_MONTH)

    var selectedDay = ""

    val datePickerDialog = DatePickerDialog(
        mContext,
        { _, _: Int, month: Int, dayOfMonth: Int ->
            selectedDay = "$dayOfMonth $month"

            when (month) {
                0 -> { timeText = selectedDay.replace(" 0", " января") }
                1 -> { timeText = selectedDay.replace(" 1", " февраля") }
                2 -> { timeText = selectedDay.replace(" 2", " марта") }
                3 -> { timeText = selectedDay.replace(" 3", " апреля") }
                4 -> { timeText = selectedDay.replace(" 4", " мая") }
                5 -> { timeText = selectedDay.replace(" 5", " июня") }
                6 -> { timeText = selectedDay.replace(" 6", " июля") }
                7 -> { timeText = selectedDay.replace(" 7", " августа") }
                8 -> { timeText = selectedDay.replace(" 8", " сентября") }
                9 -> { timeText = selectedDay.replace(" 9", " октября") }
                10 -> { timeText = selectedDay.replace(" 10", " ноября") }
                11 -> { timeText = selectedDay.replace(" 11", " декабря") }
            }
        }, mYear, mMonth, mDay
    )

    val interactionSource = remember { MutableInteractionSource() }

    if (interactionSource.collectIsPressedAsState().value) {
        datePickerDialog.show()
    }

    Column(
        modifier = Modifier
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
                softWrap = true,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "",
                tint = selectedStrokeColor,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(color = inputColor)
                    .clickable {
                        onClose()
                    }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Выберите дату",
            fontSize = 16.sp,
            color = descriptionColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            value = timeText,
            onValueChange = { timeText = it },
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
            readOnly = true,
            placeholder = { Text(text = "Сегодня, 16 апреля", fontSize = 16.sp) },
            contentPadding = PaddingValues(14.dp),
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "Выберите время",
            fontSize = 16.sp,
            color = descriptionColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            userScrollEnabled = false
        ) {
            items(7) { index ->
                AppTimeCard(
                    modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                    title = timeArray[index],
                    selectedItem = index == selectedTime
                ) {
                    selectedTime = index
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        AppButton(
            text = "Подтвердить",
            fontSize = 17.sp,
            fontWeight = FontWeight.W600
        ) {
            onChanged("$timeText ${timeArray[selectedTime]}")
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/*
Описание: Окно выбора времени анализов
Дата создания: 11.03.2023 15:10
Автор: Георгий Хасанов
*/
@Composable
fun PatientBottomSheet(
    onClose: () -> Unit,
    patientList: MutableList<User>,
    onChanged: (User) -> Unit
) {
    val mContext = LocalContext.current

    var selectedUser by rememberSaveable { mutableStateOf(-1) }

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
                "Выбор пациента",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                softWrap = true,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "",
                tint = selectedStrokeColor,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(color = inputColor)
                    .clickable {
                        onClose()
                    }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        for ((index, u) in patientList.withIndex()) {
            Card(
                elevation = 0.dp,
                backgroundColor = if (index == selectedUser) primaryColor else inputColor,
                modifier = Modifier.padding(bottom = 16.dp).clickable {
                    selectedUser = index
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = if (u.pol == "Мужской") R.drawable.ic_male else R.drawable.ic_female),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "${u.lastname} ${u.firstname}",
                        color = if (index == selectedUser) { Color.White} else { Color.Black }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        AppButton(
            text = "Добавить пациента",
            fontSize = 15.sp,
            color = primaryColor,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            borderStroke = BorderStroke(1.dp, primaryColor),
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
            enabled = selectedUser != -1,
            modifier = Modifier.fillMaxWidth()
        ) {
            onChanged(patientList[selectedUser])
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}