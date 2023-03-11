package com.example.medicmad2.view

import android.content.Intent
import androidx.compose.animation.core.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.R
import com.example.medicmad2.ui.components.AppButton
import com.example.medicmad2.ui.theme.MedicMAD2Theme
import com.example.medicmad2.ui.theme.descriptionColor
import com.example.medicmad2.ui.theme.onboardTitleColor
import com.example.medicmad2.ui.theme.primaryColor
import kotlinx.coroutines.launch

/*
Описание: Класс успеной оплаты заказа
Дата создания: 11.03.2023 25:55
Автор: Георгий Хасанов
*/
class SuccessActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mContext = LocalContext.current

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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Оплата",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W600,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(64.dp))
                        Image(
                            painter = painterResource(id = R.drawable.onboard_image_1),
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.size(200.dp)
                        )
                        Text(
                            text = "Ваш заказ успешно оплачен!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W600,
                            color = onboardTitleColor,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = "Вам осталось дождаться приезда медсестры и сдать анализы. До скорой встречи!",
                            fontSize = 14.sp,
                            color = descriptionColor,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Не забудьте ознакомиться с правилами подготовки к сдаче анализов",
                        )
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)) {
                            AppButton(
                                text = "Чек покупки",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.W600,
                                color = primaryColor,
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                borderStroke = BorderStroke(1.dp, primaryColor),
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {

                            }
                            AppButton(
                                text = "На главную",
                                contentPadding = PaddingValues(16.dp),
                                fontWeight = FontWeight.W600,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                val intent = Intent(mContext, HomeActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }


        }
    }
}