package com.payal.evaluateexpressions.util

import com.payal.evaluateexpressions.service.MathApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.mathjs.org/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val mathApiService: MathApiService by lazy {
        retrofit.create(MathApiService::class.java)
    }
}