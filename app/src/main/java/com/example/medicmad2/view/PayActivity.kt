package com.example.medicmad2.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

        val scope = rememberCoroutineScope()

        val sharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

        var addressText by rememberSaveable { mutableStateOf("") }
        var dateText by rememberSaveable { mutableStateOf("") }
        var patientText by rememberSaveable { mutableStateOf("") }
        var phoneText by rememberSaveable { mutableStateOf("") }
        var commentText by rememberSaveable { mutableStateOf("") }

        var enabled by rememberSaveable { mutableStateOf(false) }

        var cart: MutableList<CartItem> = remember { mutableStateListOf() }
        var userList: MutableList<User> = remember { mutableStateListOf() }
        var selectedUserList: MutableList<User> = remember { mutableStateListOf() }

        var addressList: MutableList<Address> = remember { mutableStateListOf() }

        var userToChange: User? by remember { mutableStateOf(null) }

        var summaryCart: MutableList<CartItem> = remember { mutableStateListOf() }

        LaunchedEffect(Unit) {
            cart.addAll(CartService().getCartData(sharedPreferences))
            userList.addAll(UserService().getUserListData(sharedPreferences))
            addressList.addAll(AddressService().getAddressListData(sharedPreferences))

            summaryCart.addAll(cart)

            for (i in summaryCart.distinct()) {
                i.count = 1
            }

            if (userList.isNotEmpty()) {
                selectedUserList.add(userList[0])

                patientText = "${selectedUserList[0].lastname} ${selectedUserList[0].firstname}"
            }
        }

        var selectedBottomSheet by rememberSaveable { mutableStateOf(0) }

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

        ModalBottomSheetLayout(
            sheetContent = {
                when (selectedBottomSheet) {
                    0 -> {
                        AddressBottomSheet(
                            onAddressSelect = {
                                addressText = "${it.addressText}, кв.${it.flat}"

                                scope.launch {
                                    sheetState.hide()
                                }
                            },
                            onAddressSave = {
                                addressList.add(it)

                                AddressService().saveAddressListData(sharedPreferences, addressList)

                                scope.launch {
                                    sheetState.hide()
                                }
                            },
                            onIconClick = {
                                scope.launch {
                                    sheetState.hide()
                                }
                            }
                        )
                    }
                    1 -> {
                        TimeBottomSheet(
                            onIconClick = {
                                scope.launch {
                                    sheetState.hide()
                                }
                            }
                        ) {
                            dateText = it
                            scope.launch {
                                sheetState.hide()
                            }
                        }
                    }
                    2 -> {
                        PatientBottomSheet(
                            userList = userList,
                            onIconClick = {
                                scope.launch {
                                    sheetState.hide()
                                }
                            }
                        ) {
                            if (userToChange == null) {
                                if (!selectedUserList.contains(it)) {
                                    selectedUserList.add(it)

                                    for (item in summaryCart.distinct()) {
                                        item.count += 1
                                    }
                                }
                            } else {
                                val userIndex = selectedUserList.indexOf(userToChange)

                                if (!selectedUserList.contains(it)) {
                                    selectedUserList.removeAt(userIndex)
                                    selectedUserList.add(userIndex, it)
                                }

                                if (selectedUserList.size < 2) {
                                    patientText = "${selectedUserList[0].lastname} ${selectedUserList[0].firstname}"
                                }

                                userToChange = null
                            }

                            scope.launch {
                                sheetState.hide()
                            }
                        }
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
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                            Spacer(modifier = Modifier.height(32.dp))
                            OrderTextField(
                                title = "Адрес *",
                                placeholder = { Text("Введите ваш адрес", fontSize = 15.sp) },
                                value = addressText,
                                onValueChange = {},
                                interactionSource = addressInteractionSource,
                                readOnly = true
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OrderTextField(
                                title = "Дата и время*",
                                placeholder = { Text("Выберите дату и время", fontSize = 15.sp) },
                                value = dateText,
                                onValueChange = {},
                                interactionSource = timeInteractionSource,
                                readOnly = true
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = buildAnnotatedString {
                                        append("Кто будет сдавать анализы? ")
                                        withStyle(SpanStyle(color = Color(0xFFFD3535))) {
                                            append("*")
                                        }
                                    },
                                    fontSize = 14.sp,
                                    color = secondaryTextColor
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                if (selectedUserList.size > 1) {
                                    for (u in selectedUserList) {
                                        OrderUserCard(
                                            user = u,
                                            cart = cart,
                                            summaryCart = summaryCart,
                                            onItemAdd = {
                                                val selectedIndex = summaryCart.indexOf(it)

                                                if (selectedIndex != -1) {
                                                    summaryCart[selectedIndex].count += 1
                                                }
                                            },
                                            onItemDelete = {
                                                val selectedIndex = summaryCart.indexOf(it)

                                                if (selectedIndex != -1) {
                                                    summaryCart[selectedIndex].count -= 1

                                                    Log.d("TAG", "PayContent: $selectedIndex ${summaryCart[selectedIndex].count}")
                                                }
                                            },
                                            onUserDelete = {
                                                val userIndex = selectedUserList.indexOf(it)

                                                if (selectedUserList.contains(it)) {
                                                    selectedUserList.removeAt(userIndex)
                                                }

                                                if (selectedUserList.size < 2) {
                                                    patientText = "${selectedUserList[0].lastname} ${selectedUserList[0].firstname}"
                                                }

                                                for (item in summaryCart.distinct()) {
                                                    item.count -= 1
                                                }

                                                scope.launch {
                                                    sheetState.hide()
                                                }
                                            }
                                        ) {
                                            userToChange = it

                                            selectedBottomSheet = 2

                                            scope.launch {
                                                sheetState.show()
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                } else {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        AppTextField(
                                            value = patientText,
                                            onValueChange = {},
                                            contentPadding = PaddingValues(16.dp),
                                            placeholder = { Text("") },
                                            leadingIcon = {
                                                Image(
                                                    painter = painterResource(id = R.drawable.ic_male),
                                                    contentDescription = "",
                                                    modifier = Modifier.size(24.dp)
                                                )
                                            },
                                            trailingIcon = {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_dropdown),
                                                    contentDescription = "",
                                                    tint = secondaryTextColor,
                                                    modifier = Modifier.clickable {
                                                        userToChange = selectedUserList[0]
                                                        selectedBottomSheet = 2

                                                        scope.launch {
                                                            sheetState.show()
                                                        }
                                                    }
                                                )
                                            },
                                            modifier = Modifier.fillMaxWidth(),
                                            readOnly = true
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            AppButton(
                                text = "Добавить еще пациента",
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White
                                ),
                                borderStroke = BorderStroke(1.dp, primaryColor),
                                color = primaryColor,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W400,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                selectedBottomSheet = 2

                                scope.launch {
                                    sheetState.show()
                                }
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            OrderTextField(
                                title = "Телефон *",
                                placeholder = { Text("", fontSize = 15.sp) },
                                value = phoneText,
                                onValueChange = { phoneText = it },
                                readOnly = false
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OrderTextField(
                                title = "Комментарий *",
                                placeholder = { Text("", fontSize = 15.sp) },
                                value = commentText,
                                onValueChange = { commentText = it },
                                readOnly = false,
                                modifier = Modifier.height(128.dp),
                            )
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(inputColor)
                        ) {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
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
                                        modifier = Modifier.size(20.dp),
                                        tint = selectedStrokeColor
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
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
                                        modifier = Modifier.size(20.dp),
                                        tint = selectedStrokeColor
                                    )
                                }
                                Spacer(modifier = Modifier.height(29.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    var cartSumPrice = 0

                                    for (item in summaryCart.distinct()) {
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
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.W600,
                                    enabled = enabled,
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}