package com.payal.weatherapplication

import javax.inject.Inject

class RepositoryImpl @Inject constructor(val api:Api) : Repository {

    override suspend fun getWheatherHistory(q: String, dt: String) : List<Forecastday>? {
        try{
            val result = api.getWheatherHistory(q, dt)
            val body = result.body()
            if(result.isSuccessful && body != null){
                return body.forecast.forecastday
            }
            return null
        } catch (e:Exception){
            return null
        }
    }
}