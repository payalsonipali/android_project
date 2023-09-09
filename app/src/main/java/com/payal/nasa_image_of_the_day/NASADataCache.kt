package com.payal.nasa_image_of_the_day

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.payal.nasa_image_of_the_day.model.ApiResponse

class NASADataCache (private val context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("nasa_cache", Context.MODE_PRIVATE)

    fun saveNASAData(data: ApiResponse) {
        val gson = Gson()
        val json = gson.toJson(data)
        preferences.edit().putString("nasa_data", json).apply()
    }

    fun getNASAData(): ApiResponse? {
        val json = preferences.getString("nasa_data", null)
        return if (json != null) {
            val gson = Gson()
            gson.fromJson(json, ApiResponse::class.java)
        } else {
            null
        }
    }
}