package com.example.medicmad2.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.medicmad2.ui.theme.MedicMAD2Theme
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.ui.theme.descriptionColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
Описание: Класс экрана успешной оплаты заказа
Дата создания: 10.03.2023 11:40
Автор: Георгий Хасанов
*/
class SuccessActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mContext = LocalContext.current

            val rotation = remember { Animatable(0f) }

            LaunchedEffect(Unit) {
                rotation.animateTo(
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            1000,
                            easing = LinearEasing
                        ),
                        repeatMode = RepeatMode.Restart
                    ),
                    targetValue = 360f
                )
            }

            MedicMAD2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp)
                            ) {
                                Text(
                                    text = "Оплата",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W600,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    ) { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.align(Alignment.Center)
                            ) {
                                Image(
                                    painter = painterResource(id = com.example.medicmad2.R.drawable.ic_loading),
                                    contentDescription = "",
                                    modifier = Modifier.rotate(rotation.value)
                                )
                                Spacer(modifier = Modifier.height(26.dp))
                                Text(
                                    text = "Связываемся с банком...",
                                    fontSize = 16.sp,
                                    color = descriptionColor
                                )
                            }
                        }
                    }
                }
            }

            val scope = rememberCoroutineScope()

            scope.launch {
                delay(1000)

                val mIntent = Intent(mContext, InfoActivity::class.java)
                mIntent.putExtra("time", intent.getStringExtra("time").toString())
                startActivity(mIntent)
            }
        }
    }
}