package com.example.medicmad2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.ui.components.AppTextButton
import com.example.medicmad2.ui.theme.MedicMAD2Theme
import com.example.medicmad2.ui.theme.descriptionColor
import com.example.medicmad2.ui.theme.onboardTitleColor
import com.example.medicmad2.ui.theme.secondaryColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OnboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMAD2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    OnboardContent()
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun OnboardContent() {
        val mContext = LocalContext.current
        val sharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

        val images = listOf(
            R.drawable.onboard_image_1,
            R.drawable.onboard_image_2,
            R.drawable.onboard_image_3,
        )
        
        var buttonText by  rememberSaveable { mutableStateOf("Пропустить") }

        val pagerState = rememberPagerState()

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect() {
                buttonText = if (it == 2) {
                    "Завершить"
                } else {
                    "Пропустить"
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppTextButton(text = buttonText) {
                    with(sharedPreferences.edit()) {
                        putBoolean("isFirstEnter", false)
                        apply()
                    }
                    
                    val intent = Intent(mContext, LoginActivity::class.java)
                    startActivity(intent)
                }
                Image(
                    painter = painterResource(id = R.drawable.onboard_logo),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            HorizontalPager(
                count = 3,
                state = pagerState
            ) { index ->
                when (index) {
                    0 -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.widthIn(max = 220.dp)
                        ) {
                            Text(
                                text = "Анализы",
                                fontSize = 20.sp,
                                color = onboardTitleColor
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(
                                text = "Экспресс сбор и получение проб",
                                fontSize = 14.sp,
                                color = descriptionColor
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 0..2) {
                    Box(modifier = Modifier
                        .size(13.dp)
                        .background(
                            if (pagerState.currentPage == i) {
                                secondaryColor
                            } else {
                                Color.White
                            }
                        )
                        .clip(CircleShape)
                        .border(0.85.dp, secondaryColor)
                        .clip(CircleShape)
                    )
                }
            }
            Image(
                painter = painterResource(id = images[pagerState.currentPage]),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}