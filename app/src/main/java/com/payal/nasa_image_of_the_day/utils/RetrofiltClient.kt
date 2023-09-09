package com.payal.nasa_image_of_the_day.utils

import com.payal.nasa_image_of_the_day.api.NasaPictureOfDayApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofiltClient {

    val BASE_URL = "https://api.nasa.gov/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val nasaPictureOfDayApi:NasaPictureOfDayApi by lazy {
        retrofit.create(NasaPictureOfDayApi::class.java)
    }
}