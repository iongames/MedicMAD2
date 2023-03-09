package com.example.medicmad2.view.screens

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.medicmad2.R
import com.example.medicmad2.ui.components.*
import com.example.medicmad2.ui.theme.descriptionColor
import com.example.medicmad2.ui.theme.placeholderColor
import com.example.medicmad2.ui.theme.secondaryTextColor
import com.example.medicmad2.viewmodel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collect

/*
Описание: Экран анализы/главная
Дата создания: 09.03.2023 9:40
Автор: Георгий Хасанов
*/
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    var swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    var searchText by rememberSaveable { mutableStateOf("") }

    var isVisible by rememberSaveable { mutableStateOf(true) }

    var isAlertDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    var categoryList = listOf(
        "Популярные",
        "Covid",
        "Комплексные",
        "Чекапы",
        "Биохимия",
        "Гормоны",
        "Иммунитет",
        "Витамины",
        "Аллергены",
        "Анализ крови",
        "Анализ мочи",
        "Анализ кала",
        "Только в клинике"
    )

    var selectedCategory by rememberSaveable { mutableStateOf("Популярные") }

    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemScrollOffset }.collect {
            isVisible = it <= 10
        }
    }

    val response by viewModel.response.observeAsState()
    LaunchedEffect(response) {
        if (response == 200) {
            isLoading = false
        }
    }

    val message by viewModel.message.observeAsState()
    LaunchedEffect(message) {
        if (message != null) {
            isAlertDialogVisible = true
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.getNews()
        viewModel.getCatalog()
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            isLoading = true

            viewModel.getNews()
            viewModel.getCatalog()
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
        ) {
            AppTextField(
                value = searchText,
                onValueChange = { searchText = it },
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "", tint = secondaryTextColor) },
                placeholder = { Text(text = "Искать анализы", fontSize = 16.sp, color = descriptionColor) },
                contentPadding = PaddingValues(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            AnimatedVisibility(
                visible = isVisible,
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        "Акции и новости",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600,
                        color = descriptionColor
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyRow {
                        items(viewModel.news.distinct()) { item ->
                            AppNewsItemCard(newsItem = item)
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Каталог анализов",
                fontSize = 17.sp,
                fontWeight = FontWeight.W600,
                color = descriptionColor,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 20.dp)
            ) {
                for (cat in categoryList) {
                    AppCategoryCard(
                        title = cat,
                        selectedItem = selectedCategory
                    ) {
                        selectedCategory = cat
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                item { Spacer(modifier = Modifier.height(24.dp)) }
                items(viewModel.catalog.filter { it.category.lowercase() == selectedCategory.lowercase() }.distinct()) { item ->
                    AppCatalogItemCard(catalogItem = item) {

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

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
}