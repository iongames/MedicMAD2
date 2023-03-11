package com.example.medicmad2.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmad2.R
import com.example.medicmad2.common.AddressService
import com.example.medicmad2.common.CartService
import com.example.medicmad2.common.UserService
import com.example.medicmad2.model.Address
import com.example.medicmad2.model.CartItem
import com.example.medicmad2.model.User
import com.example.medicmad2.ui.components.*
import com.example.medicmad2.ui.theme.*
import kotlinx.coroutines.launch

/*
Описание: Класс экрана оформления заказа
Дата создания: 10.03.2023 10:55
Автор: Георгий Хасанов
*/
class PayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMAD2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PayContent()
                }
            }
        }
    }

    /*
    Описание: Метод с контентом экрана оформления заказа
    Дата создания: 10.03.2023 10:55
    Автор: Георгий Хасанов
    */
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun PayContent() {
        val mContext = LocalContext.current
        val sharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

        var addressText by rememberSaveable { mutableStateOf("") }
        var timeText by rememberSaveable { mutableStateOf("") }
        var userText by rememberSaveable { mutableStateOf("") }

        var phoneText by rememberSaveable { mutableStateOf("") }

        var commentText by rememberSaveable { mutableStateOf("") }

        var isEnabled by rememberSaveable { mutableStateOf(false) }

        var cart: MutableList<CartItem> = remember { mutableStateListOf() }
        var addressList: MutableList<Address> = remember { mutableStateListOf() }
        val userList: MutableList<User> = remember { mutableStateListOf() }

        val selectedUserList: MutableList<User> = remember { mutableStateListOf() }

        var selectedBottomSheet by rememberSaveable { mutableStateOf(0) }

        var currentEditingUser by rememberSaveable { mutableStateOf(-1) }

        LaunchedEffect(Unit) {
            cart.addAll(CartService().getCartData(sharedPreferences))

            cart.forEach {
                it.count = 1
            }

            addressList.addAll(AddressService().getAddressListData(sharedPreferences))
            userList.addAll(UserService().getUserListData(sharedPreferences))

            if (userList.isNotEmpty()) {
                selectedUserList.add(userList[0])
                userText = "${userList[0].lastname} ${userList[0].firstname}"
            }
        }

        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

        val addressInteractionSource = remember { MutableInteractionSource() }

        if (addressInteractionSource.collectIsPressedAsState().value) {
            selectedBottomSheet = 0

            scope.launch {
                sheetState.show()
            }
        }

        val timeInteractionSource = remember { MutableInteractionSource() }

        if (timeInteractionSource.collectIsPressedAsState().value) {
            selectedBottomSheet = 1

            scope.launch {
                sheetState.show()
            }
        }

        val patientInteractionSource = remember { MutableInteractionSource() }

        if (patientInteractionSource.collectIsPressedAsState().value) {
            selectedBottomSheet = 2

            scope.launch {
                sheetState.show()
            }
        }

        ModalBottomSheetLayout(
            sheetContent = {
                when (selectedBottomSheet) {
                    0 -> {
                        AddressBottomSheet(
                            onChanged = {
                                addressText = it.addressText

                                scope.launch {
                                    sheetState.hide()
                                }
                            },
                            onAddressSave = {
                                AddressService().addAddressToList(it, addressList)
                                AddressService().saveAddressData(sharedPreferences, addressList)
                            }
                        )
                    }
                    1 -> {
                        TimeBottomSheet(
                            onClose = {
                                scope.launch {
                                    sheetState.hide()
                                }
                            },
                            onChanged = {
                                timeText = it

                                scope.launch {
                                    sheetState.hide()
                                }
                            }
                        )
                    }
                    2 -> {
                        PatientBottomSheet(
                            onClose = {
                                scope.launch {
                                    sheetState.hide()
                                }
                            },
                            patientList = userList,
                            onChanged = {
                                if (currentEditingUser != -1) {
                                    userList.removeAt(currentEditingUser)
                                    userList.add(currentEditingUser, it)

                                    currentEditingUser = -1
                                }
                            }
                        )
                    }
                }
            },
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            sheetState = sheetState
        ) {
            Scaffold(
                topBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Column {
                            AppBackButton {
                                val intent = Intent(mContext, CartActivity::class.java)
                                startActivity(intent)
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Оформление заказа",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.W700
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
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            "Адрес *",
                            fontSize = 14.sp,
                            color = secondaryTextColor,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        AppTextField(
                            value = addressText,
                            onValueChange = { addressText = it},
                            placeholder = { Text("Введите ваш адрес", fontSize = 15.sp) },
                            contentPadding = PaddingValues(14.dp),
                            readOnly = true,
                            interactionSource = addressInteractionSource,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Дата и время*",
                            fontSize = 14.sp,
                            color = secondaryTextColor,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        AppTextField(
                            value = timeText,
                            onValueChange = { timeText = it},
                            placeholder = { Text("Выберите дату и время", fontSize = 15.sp, color = descriptionColor) },
                            contentPadding = PaddingValues(14.dp),
                            readOnly = true,
                            interactionSource = timeInteractionSource,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            buildAnnotatedString {
                                append("Кто будет сдавать анализы? ")
                                withStyle(SpanStyle(color = Color(0xFFFD3535))) {
                                    append("*")
                                }
                            },
                            fontSize = 14.sp,
                            color = secondaryTextColor,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (selectedUserList.size < 2) {
                            AppTextField(
                                value = userText,
                                onValueChange = { userText = it },
                                leadingIcon = {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_male),
                                        contentDescription = "",
                                        modifier = Modifier.size(24.dp)
                                    ) },
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_dropdown),
                                        contentDescription = "",
                                        tint = secondaryTextColor,
                                        modifier = Modifier.clickable {
                                            selectedBottomSheet = 2

                                            currentEditingUser = 0

                                            scope.launch {
                                                sheetState.show()
                                            }
                                        }
                                    )
                                },
                                readOnly = true,
                                placeholder = { Text(text = "Имя Фамилия", fontSize = 16.sp, color = descriptionColor) },
                                contentPadding = PaddingValues(14.dp),
                                interactionSource = patientInteractionSource,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            )
                        } else {
                            for (su in selectedUserList) {

                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        AppButton(
                            text = "Добавить еще пациента",
                            fontSize = 15.sp,
                            color = primaryColor,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            borderStroke = BorderStroke(1.dp, primaryColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            val intent = Intent(mContext, CreateCardActivity::class.java)
                            startActivity(intent)
                        }
                        Spacer(modifier = Modifier.height(45.dp))
                        Text(
                            "Телефон *",
                            fontSize = 14.sp,
                            color = secondaryTextColor,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        AppTextField(
                            value = phoneText,
                            onValueChange = { phoneText = it},
                            placeholder = { Text("+7 (967) 078-58-37", fontSize = 15.sp) },
                            contentPadding = PaddingValues(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Text(
                                "Комментарий",
                                fontSize = 14.sp,
                                color = secondaryTextColor
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.audio),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        AppTextField(
                            value = commentText,
                            onValueChange = { commentText = it},
                            placeholder = { Text("Можете оставить свои пожелания", fontSize = 15.sp, color = descriptionColor) },
                            contentPadding = PaddingValues(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(128.dp)
                                .padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(inputColor)
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Оплата Apple Pay",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.W500
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_next),
                                    contentDescription = "",
                                    tint = selectedStrokeColor,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable {

                                        }
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Промокод",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.W500,
                                    color = descriptionColor
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_next),
                                    contentDescription = "",
                                    tint = selectedStrokeColor,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable {

                                        }
                                )
                            }
                            Spacer(modifier = Modifier.height(29.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                var cartSumPrice = 0

                                for (item in cart.distinct()) {
                                    cartSumPrice += (item.price.toInt() * item.count)
                                }

                                Text(
                                    "1 анализ",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.W500
                                )
                                Text(
                                    "$cartSumPrice ₽",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.W500
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            AppButton(
                                text = "Заказать",
                                contentPadding = PaddingValues(16.dp),
                                enabled = isEnabled,
                                fontWeight = FontWeight.W600
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}