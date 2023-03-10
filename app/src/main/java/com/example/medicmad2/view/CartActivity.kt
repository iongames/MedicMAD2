package com.example.medicmad2.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.R
import com.example.medicmad2.common.CartService
import com.example.medicmad2.model.CartItem
import com.example.medicmad2.ui.components.AppBackButton
import com.example.medicmad2.ui.theme.MedicMAD2Theme

/*
Описание: Класс экрана корзины
Дата создания: 09.03.2023 9:55
Автор: Георгий Хасанов
*/
class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMAD2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CartContent()
                }
            }
        }
    }

    @Composable
    fun CartContent() {
        val mContext = LocalContext.current
        val sharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

        var cart: MutableList<CartItem> = remember { mutableStateListOf() }

        LaunchedEffect(Unit) {
            cart.addAll(CartService().getCartData(sharedPreferences))
        }

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Column {
                        AppBackButton {
                            val intent = Intent(mContext,HomeActivity::class.java)
                            startActivity(intent)
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Корзина",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W700
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_remove_all),
                            contentDescription = "",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    cart.clear()

                                    CartService().saveCartData(sharedPreferences, cart)
                                }
                        )
                    }
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyColumn {
                        items(cart.distinct()) {

                        }
                    }
                }
            }
        }
    }
}