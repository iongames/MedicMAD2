package com.example.medicmad2.view

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.work.WorkManager
import com.example.medicmad2.R
import com.example.medicmad2.ui.components.AppButton
import com.example.medicmad2.ui.theme.MedicMAD2Theme
import com.example.medicmad2.ui.theme.descriptionColor
import com.example.medicmad2.ui.theme.onboardTitleColor
import com.example.medicmad2.ui.theme.primaryColor
import androidx.work.*
import com.example.medicmad2.common.scheduleNotification
import java.text.SimpleDateFormat
import java.time.LocalDateTime

/*
Описание: Класс экрана информации об оплате заказа
Дата создания: 10.03.2023 11:40
Автор: Георгий Хасанов
*/
class InfoActivity : ComponentActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mContext = LocalContext.current
            val mUriHandler = LocalUriHandler.current

            val text = buildAnnotatedString {
                append("Не забудьте ознакомиться с ")

                pushStringAnnotation("link", "https://medic.madskill.ru/avatar/prav.pdf")
                withStyle(SpanStyle(color = primaryColor)) {
                    append("правилами подготовки к сдаче анализов")
                }
                pop()
            }

            val time = intent.getStringExtra("time").toString()
            val sdf = SimpleDateFormat("dd MM yyyy HH:mm")
            val parsedDate = sdf.parse(time)!!

            val datetime = LocalDateTime.of(parsedDate.year, parsedDate.month, parsedDate.day, parsedDate.hours, parsedDate.minutes)
            scheduleNotification(applicationContext, datetime.minusMinutes(30))

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
                                    .padding(18.dp).padding(bottom = 30.dp)
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
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp)
                                    .padding(bottom = 32.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.onboard_image_1),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.sizeIn(
                                            maxWidth = 200.dp,
                                            maxHeight = 200.dp
                                        ).fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(30.dp))
                                    Text(
                                        text = "Ваш заказ успешно оплачен!",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.W600,
                                        color = onboardTitleColor,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Вам осталось дождаться приезда медсестры и сдать анализы. До скорой встречи!",
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        color = descriptionColor,
                                        modifier = Modifier.widthIn(max = 290.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    ClickableText(
                                        text = text,
                                        style = TextStyle(
                                            textAlign = TextAlign.Center,
                                            fontSize = 14.sp,
                                            color = descriptionColor
                                        ),
                                        modifier = Modifier.widthIn(max = 290.dp)
                                    ) { offset ->
                                        text.getStringAnnotations(
                                            tag = "link",
                                            offset, offset
                                        ).firstOrNull().let { mUriHandler.openUri(it!!.item) }
                                    }
                                }
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    AppButton(
                                        text = "Чек покупки",
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color.White
                                        ),
                                        borderStroke = BorderStroke(1.dp, primaryColor),
                                        color = primaryColor,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.W600,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {

                                    }
                                    Spacer(modifier = Modifier.height(20.dp))
                                    AppButton(
                                        text = "На главную",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.W600,
                                        modifier = Modifier.fillMaxWidth()
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
    }
}