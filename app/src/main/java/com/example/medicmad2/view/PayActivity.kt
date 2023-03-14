package com.example.medicmad2.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
<<<<<<< HEAD
=======
import androidx.compose.foundation.gestures.scrollable
>>>>>>> Session-5
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
<<<<<<< HEAD
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
=======
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
>>>>>>> Session-5
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
<<<<<<< HEAD
=======
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
>>>>>>> Session-5
import com.example.medicmad2.R
import com.example.medicmad2.common.AddressService
import com.example.medicmad2.common.CartService
import com.example.medicmad2.common.UserService
import com.example.medicmad2.model.Address
import com.example.medicmad2.model.CartItem
import com.example.medicmad2.model.User
import com.example.medicmad2.ui.components.*
import com.example.medicmad2.ui.theme.*
<<<<<<< HEAD
=======
import com.example.medicmad2.viewmodel.OrderViewModel
>>>>>>> Session-5
import kotlinx.coroutines.launch

/*
Описание: Класс экрана оформления заказа
Дата создания: 11.03.2023 10:55
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
    Дата создания: 11.03.2023 10:55
    Автор: Георгий Хасанов
    */
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun PayContent() {
        val mContext = LocalContext.current
<<<<<<< HEAD
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

        var selectedUsersCart: MutableList<CartItem> = remember { mutableStateListOf() }
        val selectedUserList: MutableList<User> = remember { mutableStateListOf() }

        var selectedBottomSheet by rememberSaveable { mutableStateOf(0) }

        var currentEditingUser by rememberSaveable { mutableStateOf(-1) }

        LaunchedEffect(Unit) {
            cart.addAll(CartService().getCartData(sharedPreferences))
            selectedUsersCart.addAll(cart)

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

=======
        val viewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

        val scope = rememberCoroutineScope()

        val sharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

        var addressText by rememberSaveable { mutableStateOf("") }
        var dateText by rememberSaveable { mutableStateOf("") }
        var trueDateText by rememberSaveable { mutableStateOf("") }
        var patientText by rememberSaveable { mutableStateOf("") }
        var phoneText by rememberSaveable { mutableStateOf("") }
        var commentText by rememberSaveable { mutableStateOf("") }

        var enabled by rememberSaveable { mutableStateOf(false) }

        var isAlertDialogVisible by rememberSaveable { mutableStateOf(false) }
        var isLoading by rememberSaveable { mutableStateOf(false) }

        var cart: MutableList<CartItem> = remember { mutableStateListOf() }
        var userList: MutableList<User> = remember { mutableStateListOf() }
        var selectedUserList: MutableList<User> = remember { mutableStateListOf() }

        var addressList: MutableList<Address> = remember { mutableStateListOf() }

        var userToChange: User? by remember { mutableStateOf(null) }

        LaunchedEffect(Unit) {
            cart.addAll(CartService().getCartData(sharedPreferences))
            userList.addAll(UserService().getUserListData(sharedPreferences))
            addressList.addAll(AddressService().getAddressListData(sharedPreferences))

            for (i in cart.distinct()) {
                i.count = 1
            }

            if (userList.isNotEmpty()) {
                selectedUserList.add(userList[0])

                patientText = "${selectedUserList[0].lastname} ${selectedUserList[0].firstname}"
            }

            for (su in selectedUserList) {
                su.cart = cart
            }
        }

        var selectedBottomSheet by rememberSaveable { mutableStateOf(0) }

        val addressInteractionSource = remember { MutableInteractionSource() }
>>>>>>> Session-5
        if (addressInteractionSource.collectIsPressedAsState().value) {
            selectedBottomSheet = 0

            scope.launch {
                sheetState.show()
            }
        }

        val timeInteractionSource = remember { MutableInteractionSource() }
<<<<<<< HEAD

=======
>>>>>>> Session-5
        if (timeInteractionSource.collectIsPressedAsState().value) {
            selectedBottomSheet = 1

            scope.launch {
                sheetState.show()
            }
        }

<<<<<<< HEAD
        val patientInteractionSource = remember { MutableInteractionSource() }

        if (patientInteractionSource.collectIsPressedAsState().value) {
            selectedBottomSheet = 2

            scope.launch {
                sheetState.show()
=======
        val response by viewModel.response.observeAsState()
        LaunchedEffect(response) {
            if (response == 200) {
                val intent = Intent(mContext, SuccessActivity::class.java)
                intent.putExtra("time", trueDateText)
                startActivity(intent)
            }

            isLoading = false
        }

        val message by viewModel.message.observeAsState()
        LaunchedEffect(message) {
            if (message != null) {
                isAlertDialogVisible = true
                isLoading = false
>>>>>>> Session-5
            }
        }

        ModalBottomSheetLayout(
            sheetContent = {
                when (selectedBottomSheet) {
                    0 -> {
                        AddressBottomSheet(
<<<<<<< HEAD
                            onChanged = {
                                addressText = it.addressText

                                if (phoneText.isNotEmpty() && addressText.isNotEmpty() && timeText.isNotEmpty()) {
                                    isEnabled = true
                                } else {
                                    isEnabled = false
                                }
=======
                            onAddressSelect = {
                                addressText = "${it.addressText}, кв.${it.flat}"

                                scope.launch {
                                    sheetState.hide()
                                }
                            },
                            onAddressSave = {
                                addressList.add(it)

                                AddressService().saveAddressListData(sharedPreferences, addressList)
>>>>>>> Session-5

                                scope.launch {
                                    sheetState.hide()
                                }
                            },
<<<<<<< HEAD
                            onAddressSave = {
                                AddressService().addAddressToList(it, addressList)
                                AddressService().saveAddressData(sharedPreferences, addressList)
=======
                            onIconClick = {
                                scope.launch {
                                    sheetState.hide()
                                }
>>>>>>> Session-5
                            }
                        )
                    }
                    1 -> {
                        TimeBottomSheet(
<<<<<<< HEAD
                            onClose = {
                                scope.launch {
                                    sheetState.hide()
                                }
                            },
                            onChanged = {
                                timeText = it

                                if (phoneText.isNotEmpty() && addressText.isNotEmpty() && timeText.isNotEmpty()) {
                                    isEnabled = true
                                } else {
                                    isEnabled = false
                                }

=======
                            onIconClick = {
>>>>>>> Session-5
                                scope.launch {
                                    sheetState.hide()
                                }
                            }
<<<<<<< HEAD
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
                                } else {
                                    if (!userList.contains(it)) {
                                        userList.add(it)

                                        for (item in selectedUsersCart) {
                                            item.count += 1
                                        }
                                    }
                                }
                            }
                        )
=======
                        ) { date, trueDate ->
                            dateText = date
                            trueDateText = trueDate
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

                                    val index = selectedUserList.indexOfFirst { user -> user == it }

                                    if (index != -1) {
                                        selectedUserList[index].cart.clear()
                                        selectedUserList[index].cart.addAll(cart)
                                    }

                                    //for (item in summaryCart.distinct()) {
                                    //    item.count += 1
                                    //}
                                }
                            } else {
                                val userIndex = selectedUserList.indexOf(userToChange)

                                if (!selectedUserList.contains(it)) {
                                    selectedUserList.removeAt(userIndex)
                                    selectedUserList.add(userIndex, it)

                                    if (userIndex != -1) {
                                        selectedUserList[userIndex].cart.clear()
                                        selectedUserList[userIndex].cart.addAll(cart)
                                    }
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
>>>>>>> Session-5
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
<<<<<<< HEAD
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
                            onValueChange = { timeText = it },
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
                                OrderUserCard(
                                    su,
                                    cart,
                                    onUserChange = {
                                        selectedBottomSheet = 2

                                        currentEditingUser = selectedUserList.indexOf(su)

                                        scope.launch {
                                            sheetState.show()
                                        }
                                    },
                                    onUserDelete = {
                                        if (userList.contains(it)) {
                                            userList.remove(it)

                                            for (item in selectedUsersCart) {
                                                if (item.count - 1 > 0) {
                                                    item.count -= 1
                                                } else {
                                                    selectedUsersCart.remove(item)
                                                }
                                            }
                                        }
                                    },
                                    onCartAddItem = {
                                        for (item in it) {
                                            val indexOfItem = selectedUsersCart.indexOf(item)

                                            if (indexOfItem != -1) {
                                                selectedUsersCart[indexOfItem].count += 1
                                            } else {

                                            }
                                        }
                                    },
                                    onCartDeleteItem = {
                                        for (item in it) {
                                            val indexOfItem = selectedUsersCart.indexOf(item)

                                            if (indexOfItem != -1) {
                                                if (selectedUsersCart[indexOfItem].count - 1 > 0) {
                                                    selectedUsersCart[indexOfItem].count -= 1
                                                } else {
                                                    selectedUsersCart.removeAt(indexOfItem)
                                                }
                                            }
                                        }
                                    }
                                )
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
                            selectedBottomSheet = 2

                            scope.launch {
                                sheetState.show()
                            }
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
                            onValueChange = {
                                phoneText = it

                                if (phoneText.isNotEmpty() && addressText.isNotEmpty() && timeText.isNotEmpty()) {
                                    isEnabled = true
                                } else {
                                    isEnabled = false
                                }
                            },
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
=======
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
                                    for ((userIndex, u) in selectedUserList.withIndex()) {
                                        OrderUserCard(
                                            user = u,
                                            cart = cart,
                                            onUserSave = {
                                                if (selectedUserList.contains(u)) {
                                                    selectedUserList.removeAt(userIndex)
                                                    selectedUserList.add(userIndex, it)
                                                }
                                            },
                                            onUserDelete = {
                                                val uIndex = selectedUserList.indexOf(it)

                                                if (selectedUserList.contains(it)) {
                                                    selectedUserList.removeAt(uIndex)
                                                }

                                                if (selectedUserList.size < 2) {
                                                    patientText = "${selectedUserList[0].lastname} ${selectedUserList[0].firstname}"
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
                                onValueChange = {
                                    phoneText = it

                                    enabled = addressText.isNotEmpty() && dateText.isNotEmpty() && phoneText.isNotEmpty()
                                },
                                readOnly = false
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
>>>>>>> Session-5
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
<<<<<<< HEAD
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

                                for (item in selectedUsersCart.distinct()) {
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
                                fontWeight = FontWeight.W600,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val intent = Intent(mContext, ProcessingActivity::class.java)
                                startActivity(intent)
=======
                                    text = "Комментарий",
                                    fontSize = 14.sp,
                                    color = secondaryTextColor
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.ic_audio),
                                    contentDescription = "",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            AppTextField(
                                value = commentText,
                                onValueChange = { commentText = it },
                                contentPadding = PaddingValues(16.dp),
                                placeholder = { Text("Можете оставить свои пожелания", fontSize = 15.sp, color = descriptionColor) },
                                modifier = Modifier.fillMaxWidth()
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
                                    var cartSumAnalysis = 0

                                    for (u in selectedUserList.distinct()) {
                                        for (item in u.cart.distinct()) {
                                            cartSumPrice += item.price.toInt() * item.count
                                            cartSumAnalysis += item.count

                                            Log.d("T", "PayContent: ${cartSumPrice}")
                                        }
                                    }

                                    Text(
                                        "$cartSumAnalysis анализ",
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
                                    isLoading = true
                                    viewModel.createOrder(sharedPreferences.getString("token", "").toString(), addressText, dateText, phoneText, commentText, selectedUserList)
                                }
>>>>>>> Session-5
                            }
                        }
                    }
                }
            }
        }
<<<<<<< HEAD
=======

        if (isAlertDialogVisible) {
            AlertDialog(
                onDismissRequest = { isAlertDialogVisible = false },
                title = { Text("Ошибка") },
                text = { Text(viewModel.message.value.toString()) },
                buttons = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                        AppTextButton(text = "OK") {
                            isAlertDialogVisible = false
                        }
                    }
                }
            )
        }

        if (isLoading) {
            Dialog(onDismissRequest = {}) {
                CircularProgressIndicator()
            }
        }
>>>>>>> Session-5
    }
}