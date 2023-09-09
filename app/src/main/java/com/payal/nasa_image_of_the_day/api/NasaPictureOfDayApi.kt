package com.payal.nasa_image_of_the_day.api

import android.content.Context
import com.payal.nasa_image_of_the_day.NASADataCache
import com.payal.nasa_image_of_the_day.model.ApiResponse
import com.payal.nasa_image_of_the_day.utils.RetrofiltClient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class NasaPictureOfDayService(context: Context) {

    private val cache = NASADataCache(context)
    suspend fun getPictureOfTheDay(apiKey: String): ApiResult<ApiResponse> {
        try {
            val response = RetrofiltClient.nasaPictureOfDayApi.getPictureOfTheDay(apiKey)
            val data = response.body()
            if (response.isSuccessful && data != null) {
                // Save new data in cache
                cache.saveNASAData(data)
                return ApiResult.Success(data)
            }
            return ApiResult.Error("Failed to fetch data, please try again ! ${response.code()}")
        } catch (e: Exception) {
            return ApiResult.Error("Something went wrong.Please try after some time! ${e}")
        }
    }

}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val error: String) : ApiResult<Nothing>()
}

interface NasaPictureOfDayApi {
    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String): Response<ApiResponse>
}