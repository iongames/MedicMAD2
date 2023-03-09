package com.example.medicmad2.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicmad2.common.ApiService
import com.example.medicmad2.model.CatalogItem
import com.example.medicmad2.model.NewsItem
import kotlinx.coroutines.launch

/*
Описание: Логика экранов Главная, Профиль
Дата создания: 09.03.2023 9:40
Автор: Георгий Хасанов
*/
@SuppressLint("MutableCollectionMutableState")
class HomeViewModel: ViewModel() {
    private val _news: MutableList<NewsItem> = mutableStateListOf()
    val news: List<NewsItem> by mutableStateOf(_news)

    private val _catalog: MutableList<CatalogItem> = mutableStateListOf()
    val catalog: List<CatalogItem> by mutableStateOf(_catalog)

    val message = MutableLiveData<String>()
    var response = MutableLiveData<Int>()

    /*
    Описание: Функция получения новостей и акций с сервера
    Дата создания: 09.03.2023 9:40
    Автор: Георгий Хасанов
    */
    fun getNews() {
        _news.clear()
        message.value = null
        response.value = null

        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val json = apiService.getNews()

                _news.addAll(json)
                response.value = 200
            } catch (e: Exception) {
                message.value = e.message
            }
        }
    }

    /*
    Описание: Функция получения каталога анализов с сервера
    Дата создания: 09.03.2023 9:40
    Автор: Георгий Хасанов
    */
    fun getCatalog() {
        _catalog.clear()
        message.value = null
        response.value = null

        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val json = apiService.getCatalog()

                _catalog.addAll(json)
                response.value = 200
            } catch (e: Exception) {
                message.value = e.message
            }
        }
    }
}