package com.payal.weatherapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("history.json")
    suspend fun getWheatherHistory(@Query("q") q:String, @Query("dt") dt:String) : Response<Root>

}