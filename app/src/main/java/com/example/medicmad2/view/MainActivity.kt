package com.example.medicmad2.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.medicmad2.R
import com.example.medicmad2.ui.theme.MedicMAD2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMAD2Theme {
                val mContext = LocalContext.current
                val sharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.splash_bg),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                if (sharedPreferences.getBoolean("isFirstEnter", true)) {
                    val intent = Intent(mContext, OnboardActivity::class.java)
                    startActivity(intent)
                } else {
                    if (sharedPreferences.getString("token", "") != "") {
                        if (sharedPreferences.getString("password", "") != "") {
                            val intent = Intent(mContext, CreatePasswordActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(mContext, CreateCardActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        val intent = Intent(mContext, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}