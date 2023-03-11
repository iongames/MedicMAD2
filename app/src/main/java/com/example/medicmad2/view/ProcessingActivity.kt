package com.example.medicmad2.view

import android.content.Intent
import androidx.compose.animation.core.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.R
import com.example.medicmad2.ui.theme.MedicMAD2Theme
import com.example.medicmad2.ui.theme.descriptionColor

/*
Описание: Класс экрана оплаты заказа
Дата создания: 11.03.2023 25:55
Автор: Георгий Хасанов
*/
class ProcessingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var rotation = remember { Animatable(0f) }

            LaunchedEffect(Unit) {
                rotation.animateTo(
                    360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }

            MedicMAD2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Оплата",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W600,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_loading),
                            contentDescription = "",
                            modifier = Modifier
                                .size(66.dp)
                                .rotate(rotation.value)
                        )
                        Spacer(modifier = Modifier.height(26.dp))
                        Text(
                            "Связываемся с банком...",
                            fontSize = 16.sp,
                            color = descriptionColor
                        )
                    }
                }
            }

            val intent = Intent(this, SuccessActivity::class.java)
            startActivity(intent)
        }
    }
}