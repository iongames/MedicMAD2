package com.example.medicmad2.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.medicmad2.R
import com.example.medicmad2.ui.components.AppButton
import com.example.medicmad2.ui.components.AppTextButton
import com.example.medicmad2.ui.components.AppTextField
import com.example.medicmad2.ui.theme.*
import com.example.medicmad2.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMAD2Theme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("login"),
                    color = MaterialTheme.colors.background
                ) {
                    LoginContent()
                }
            }
        }
    }

    /*
    Описание: Контент экрана входа в аккаунт
    Дата создания: 08.03.2023 12:30
    Автор: Георгий Хасанов
    */
    @Composable
    fun LoginContent() {
        val mContext = LocalContext.current

        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        var email by rememberSaveable { mutableStateOf("") }

        var isEnabled by rememberSaveable { mutableStateOf(false) }

        var isAlertDialogVisible by rememberSaveable { mutableStateOf(false) }

        val message by viewModel.message.observeAsState()
        LaunchedEffect(message) {
            if (message != null) {
                isAlertDialogVisible = true
            }
        }

        val responseCode by viewModel.responseCode.observeAsState()
        LaunchedEffect(responseCode) {
            if (responseCode == 200) {
                val intent = Intent(mContext, EmailCodeActivity::class.java)
                intent.putExtra("email", email)

                startActivity(intent)
            }
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 60.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_hello),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Добро пожаловать!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W700,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(23.dp))
                Text(
                    "Войдите, чтобы пользоваться функциями приложения",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    "Вход по E-mail",
                    fontSize = 14.sp,
                    color = secondaryTextColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = {
                        email = it

                        isEnabled = it.isNotEmpty()
                    },
                    singleLine = true,
                    placeholder = { Text("example@mail.ru", fontSize = 15.sp, color = placeholderColor) },
                    contentPadding = PaddingValues(14.dp)
                )
                Spacer(modifier = Modifier.height(34.dp))
                AppButton(
                    text = "Далее",
                    enabled = isEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (Regex("^[a-zA-Z0-9]+@([a-zA-Z0-9.]+)+[a-z]{2,}$").matches(email)) {
                        viewModel.sendEmailCode(email)
                    } else {
                        Toast.makeText(mContext, "Неправильный формат E-Mail", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Или войдите с помощью",
                    fontSize = 15.sp,
                    color = descriptionColor,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                AppButton(
                    text = "Войти с Яндекс",
                    fontWeight = FontWeight.W500,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    borderStroke = BorderStroke(1.dp, strokeColor),
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                ) {

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
    }
}