package com.example.medicmad2.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.medicmad2.ui.components.AppBackButton
import com.example.medicmad2.ui.components.AppTextButton
import com.example.medicmad2.ui.components.AppTextField
import com.example.medicmad2.ui.theme.MedicMAD2Theme
import com.example.medicmad2.ui.theme.descriptionColor
import com.example.medicmad2.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

class EmailCodeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMAD2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EmailCodeContent()
                }
            }
        }
    }

    /*
    Описание: Контент экрана ввода кода из Email
    Дата создания: 08.03.2023 12:50
    Автор: Георгий Хасанов
    */
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun EmailCodeContent() {
        val mContext = LocalContext.current
        val focus = LocalFocusManager.current

        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val sharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)
        
        var emailCode1 by rememberSaveable { mutableStateOf("") }
        var emailCode2 by rememberSaveable { mutableStateOf("") }
        var emailCode3 by rememberSaveable { mutableStateOf("") }
        var emailCode4 by rememberSaveable { mutableStateOf("") }

        var isAlertDialogVisible by rememberSaveable { mutableStateOf(false) }

        val message by viewModel.message.observeAsState()
        LaunchedEffect(message) {
            if (message != null) {
                isAlertDialogVisible = true
            }
        }

        val token by viewModel.token.observeAsState()
        LaunchedEffect(token) {
            if (token != null) {
                with(sharedPreferences.edit()) {
                    putString("token", token)
                    apply()
                }

                val intent = Intent(mContext, CreatePasswordActivity::class.java)
                startActivity(intent)
            }
        }

        var timer by rememberSaveable { mutableStateOf(60) }
        LaunchedEffect(timer) {
            delay(1000)
            if (timer > 0) {
                timer -= 1
            } else {
                timer = 60

                viewModel.sendEmailCode(intent.getStringExtra("email").toString())
            }
        }

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    AppBackButton {
                        onBackPressed()
                    }
                }
            }
        ) { padding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .widthIn(max = 350.dp)
                        .padding(20.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        "Введите код из E-mail",
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AppTextField(
                            modifier = Modifier.size(48.dp),
                            value = emailCode1,
                            onValueChange = {
                                if (it.length <= 1) {
                                    emailCode1 = it
                                    focus.moveFocus(FocusDirection.Next)
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(textAlign = TextAlign.Center, lineHeight = 0.sp, fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppTextField(
                            modifier = Modifier.size(48.dp),
                            value = emailCode2,
                            onValueChange = {
                                if (it.length <= 1) {
                                    emailCode2 = it
                                    focus.moveFocus(FocusDirection.Next)
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(textAlign = TextAlign.Center, lineHeight = 0.sp, fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppTextField(
                            modifier = Modifier.size(48.dp),
                            value = emailCode3,
                            onValueChange = {
                                if (it.length <= 1) {
                                    emailCode3 = it
                                    focus.moveFocus(FocusDirection.Next)
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(textAlign = TextAlign.Center, lineHeight = 0.sp, fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppTextField(
                            modifier = Modifier.size(48.dp),
                            value = emailCode4,
                            onValueChange = {
                                if (it.length <= 1) {
                                    emailCode4 = it
                                    focus.clearFocus()

                                    viewModel.checkEmailCode(intent.getStringExtra("email").toString(), "$emailCode1$emailCode2$emailCode3$emailCode4")
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(textAlign = TextAlign.Center, lineHeight = 0.sp, fontSize = 20.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Отправить код повторно можно будет через $timer секунд",
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = descriptionColor
                    )
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