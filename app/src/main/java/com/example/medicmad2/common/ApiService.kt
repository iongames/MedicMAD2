package com.example.medicmad2.common

import com.example.medicmad2.model.CatalogItem
import com.example.medicmad2.model.NewsItem
import com.example.medicmad2.model.User
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @Headers(
        "accept: application/json"
    )
    @POST("sendCode")
    suspend fun sendEmailCode(@Header("email") email: String): Response<JsonObject>

    @Headers(
        "accept: application/json"
    )
    @POST("signin")
    suspend fun checkEmailCode(@Header("email") email: String, @Header("code") code: String): Response<JsonObject>

    @Headers(
        "accept: application/json",
        "Content-Type: application/json"
    )
    @POST("createProfile")
    suspend fun createProfileCard(@Header("Authorization") token: String, @Body user: User): Response<JsonObject>

    @Headers(
        "accept: application/json"
    )
    @GET("news")
    suspend fun getNews(): List<NewsItem>

    @Headers(
        "accept: application/json"
    )
    @GET("catalog")
    suspend fun getCatalog(): List<CatalogItem>

    companion object {
        var apiService: ApiService? = null

        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://medic.madskill.ru/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }

            return apiService!!
        }
    }
}