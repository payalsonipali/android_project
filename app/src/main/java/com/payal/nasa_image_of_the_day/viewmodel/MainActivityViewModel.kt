package com.payal.nasa_image_of_the_day.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payal.nasa_image_of_the_day.NASADataCache
import com.payal.nasa_image_of_the_day.model.ApiResponse
import com.payal.nasa_image_of_the_day.utils.RetrofiltClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(val context: Context) : ViewModel() {

    private val cache = NASADataCache(context)

    // LiveData for the latest NASA data
    private val _nasaData = MutableLiveData<ApiResponse>()
    val nasaData: LiveData<ApiResponse> = _nasaData

    // Fetch new data from the API
    fun fetchNASAData(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofiltClient.nasaPictureOfDayApi.getPictureOfTheDay(apiKey)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        Log.d("tagggg", "data called $data")
                        // Save new data in cache
                        cache.saveNASAData(data)
                        // Update LiveData
                        Log.d("tagggg","posteddddd called")

                        _nasaData.postValue(data)
                    }
                }
            } catch (e: Exception) {
                // Handle API request failure
            }
        }
    }

    // Initialize LiveData with cached data on ViewModel creation
    fun initCache() {
        val cachedData = cache.getNASAData()
        if (cachedData != null) {
            Log.d("tagggg","caheddata called")
            _nasaData.postValue(cachedData)
        }
    }
}