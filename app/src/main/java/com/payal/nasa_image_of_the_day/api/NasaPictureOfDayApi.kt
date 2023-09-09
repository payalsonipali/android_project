package com.payal.nasa_image_of_the_day.api

import com.payal.nasa_image_of_the_day.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaPictureOfDayApi {

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String): Response<ApiResponse>
}