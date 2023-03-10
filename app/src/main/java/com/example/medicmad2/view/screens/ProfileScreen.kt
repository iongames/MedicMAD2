package com.example.medicmad2.view.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import com.example.medicmad2.R
import com.example.medicmad2.model.User
import com.example.medicmad2.ui.components.AppButton
import com.example.medicmad2.ui.components.AppTextButton
import com.example.medicmad2.ui.components.AppTextField
import com.example.medicmad2.ui.theme.*
import com.example.medicmad2.view.HomeActivity
import com.example.medicmad2.viewmodel.LoginViewModel

/*
Описание: Экран профиля пользователя
Дата создания: 09.03.2023 9:40
Автор: Георгий Хасанов
*/
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(viewModel: LoginViewModel) {
    val mContext = LocalContext.current

    val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

    var firstName by rememberSaveable { mutableStateOf("") }
    var patronymic by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var birthday by rememberSaveable { mutableStateOf("") }

    var gender by rememberSaveable { mutableStateOf("") }

    var isEnabled by rememberSaveable { mutableStateOf(false) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    var isAlertDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    val message by viewModel.message.observeAsState()
    LaunchedEffect(message) {
        if (message != null) {
            isAlertDialogVisible = true
            isLoading = false
        }
    }

    val response by viewModel.cardResponseCode.observeAsState()
    LaunchedEffect(response) {
        if (response == 200) {
            isLoading = false

            val intent = Intent(mContext, HomeActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp).padding(top = 32.dp, bottom = 16.dp)
            ) {
                Text(
                    "Создание карты пациента",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700,
                    softWrap = true,
                    modifier = Modifier.fillMaxWidth(0.6f)
                )
                AppTextButton(
                    text = "Пропустить",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400
                ) {
                    val intent = Intent(mContext, HomeActivity::class.java)
                    mContext.startActivity(intent)
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)) {
                Text(
                    "Без карты пациента вы не сможете заказать анализы.",
                    fontSize = 14.sp,
                    color = descriptionColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "В картах пациентов будут храниться результаты анализов вас и ваших близких.",
                    fontSize = 14.sp,
                    color = descriptionColor
                )
                Spacer(modifier = Modifier.height(32.dp))
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = firstName,
                    onValueChange = {
                        firstName = it

                        isEnabled = firstName.isNotEmpty() && patronymic.isNotEmpty() && lastName.isNotEmpty() && birthday.isNotEmpty() && gender.isNotEmpty()
                    },
                    borderStroke = if (firstName.isNotEmpty()) { BorderStroke(1.dp, selectedStrokeColor) } else { BorderStroke(1.dp, strokeColor) },
                    singleLine = true,
                    placeholder = { Text("Имя", fontSize = 15.sp, color = placeholderColor) },
                    contentPadding = PaddingValues(14.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = patronymic,
                    onValueChange = {
                        patronymic = it

                        isEnabled = firstName.isNotEmpty() && patronymic.isNotEmpty() && lastName.isNotEmpty() && birthday.isNotEmpty() && gender.isNotEmpty()
                    },
                    borderStroke = if (patronymic.isNotEmpty()) { BorderStroke(1.dp, selectedStrokeColor) } else { BorderStroke(1.dp, strokeColor) },
                    singleLine = true,
                    placeholder = { Text("Отчество", fontSize = 15.sp, color = placeholderColor) },
                    contentPadding = PaddingValues(14.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = lastName,
                    onValueChange = {
                        lastName = it

                        isEnabled = firstName.isNotEmpty() && patronymic.isNotEmpty() && lastName.isNotEmpty() && birthday.isNotEmpty() && gender.isNotEmpty()
                    },
                    borderStroke = if (lastName.isNotEmpty()) { BorderStroke(1.dp, selectedStrokeColor) } else { BorderStroke(1.dp, strokeColor) },
                    singleLine = true,
                    placeholder = { Text("Фамилия", fontSize = 15.sp, color = placeholderColor) },
                    contentPadding = PaddingValues(14.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = birthday,
                    onValueChange = {
                        birthday = it

                        isEnabled = firstName.isNotEmpty() && patronymic.isNotEmpty() && lastName.isNotEmpty() && birthday.isNotEmpty() && gender.isNotEmpty()
                    },
                    borderStroke = if (birthday.isNotEmpty()) { BorderStroke(1.dp, selectedStrokeColor) } else { BorderStroke(1.dp, strokeColor) },
                    singleLine = true,
                    placeholder = { Text("Дата рождения", fontSize = 15.sp, color = placeholderColor) },
                    contentPadding = PaddingValues(14.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = false } ) {
                    AppTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = gender,
                        onValueChange = {
                            gender = it
                        },
                        borderStroke = if (gender.isNotEmpty()) { BorderStroke(1.dp, selectedStrokeColor) } else { BorderStroke(1.dp, strokeColor) },
                        singleLine = true,
                        readOnly = true,
                        placeholder = { Text("Пол", fontSize = 15.sp, color = placeholderColor) },
                        trailingIcon = {
                            IconButton(onClick = { isExpanded = !isExpanded }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_dropdown),
                                    contentDescription = "",
                                    tint = secondaryTextColor
                                )
                            }
                        },
                        contentPadding = PaddingValues(14.dp)
                    )
                    DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                        DropdownMenuItem(onClick = {
                            gender = "Мужской"

                            isEnabled = firstName.isNotEmpty() && patronymic.isNotEmpty() && lastName.isNotEmpty() && birthday.isNotEmpty() && gender.isNotEmpty()
                        }) {
                            Text("Мужской")
                        }
                        DropdownMenuItem(onClick = {
                            gender = "Женский"

                            isEnabled = firstName.isNotEmpty() && patronymic.isNotEmpty() && lastName.isNotEmpty() && birthday.isNotEmpty() && gender.isNotEmpty()
                        }) {
                            Text("Женский")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
                AppButton(
                    text = "Создать",
                    enabled = isEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    isLoading = true

                    viewModel.updateProfileCard(
                        sharedPreferences.getString("token", "").toString(),
                        user = User(
                            firstname = firstName,
                            middlename = patronymic,
                            lastname = lastName,
                            bith = birthday,
                            pol = gender,
                            image = ""
                        )
                    )
                }
            }
        }
    }

    if (isAlertDialogVisible) {
        AlertDialog(
            onDismissRequest = { isAlertDialogVisible = false },
            title = { Text("Ошибка") },
            text = { Text(viewModel.message.value.toString()) },
            buttons = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    AppTextButton(text = "OK") {
                        isAlertDialogVisible = false
                    }
                }
            }
        )
    }

    if (isLoading) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }
}