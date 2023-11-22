package com.payal.weatherapplication

interface Repository {

    suspend fun getWheatherHistory(q:String, dt:String) : List<Forecastday>?
}