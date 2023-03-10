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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.R
import com.example.medicmad2.common.CartService
import com.example.medicmad2.model.CartItem
import com.example.medicmad2.ui.components.AppBackButton
import com.example.medicmad2.ui.components.AppButton
import com.example.medicmad2.ui.components.AppCartItemCard
import com.example.medicmad2.ui.theme.MedicMAD2Theme
import com.example.medicmad2.ui.theme.selectedStrokeColor

/*
Описание: Класс экрана корзины
Дата создания: 10.03.2023 9:55
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

    /*
    Описание: Контент экрана корзины
    Дата создания: 10.03.2023 9:55
    Автор: Георгий Хасанов
    */
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
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Корзина",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W700
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_remove_all),
                                contentDescription = "",
                                tint = selectedStrokeColor,
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
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    LazyColumn(modifier = Modifier.padding(horizontal = 20.dp)) {
                        item { Spacer(modifier = Modifier.height(12.dp)) }
                        items(cart.distinct()) { item ->
                            AppCartItemCard(
                                item,
                                onItemAdd = {
                                    cart = CartService().addToCart(item, cart)
                                    CartService().saveCartData(sharedPreferences, cart)

                                    cart.clear()
                                    cart.addAll(CartService().getCartData(sharedPreferences))
                                },
                                onItemDelete = {
                                    val itemIndex = cart.indexOfFirst { it.id == item.id }

                                    cart = CartService().removeFromCart(itemIndex, cart)
                                    CartService().saveCartData(sharedPreferences, cart)

                                    cart.clear()
                                    cart.addAll(CartService().getCartData(sharedPreferences))
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item { Spacer(modifier = Modifier.height(24.dp)) }
                        item {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Сумма",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W600
                                )

                                var cartSumPrice = 0

                                for (item in cart.distinct()) {
                                    cartSumPrice += (item.price.toInt() * item.count)
                                }

                                Text(
                                    "$cartSumPrice ₽",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W600
                                )
                            }
                        }
                    }
                    AppButton(
                        text = "Перейти к оформлению заказа",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .padding(bottom = 32.dp)
                    ) {
                        val intent = Intent(mContext, PayActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}