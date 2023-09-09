package com.payal.nasa_image_of_the_day.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payal.nasa_image_of_the_day.NASADataCache
import com.payal.nasa_image_of_the_day.api.ApiResult
import com.payal.nasa_image_of_the_day.api.NasaPictureOfDayService
import com.payal.nasa_image_of_the_day.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(val context: Context) : ViewModel() {
    private val cache = NASADataCache(context)
    // LiveData for the latest NASA data
    private val _nasaData = MutableLiveData<ApiResponse>()
    val nasaData: LiveData<ApiResponse> = _nasaData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // Fetch new data from the API
    fun fetchNASAData(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = NasaPictureOfDayService(context).getPictureOfTheDay(apiKey)
            when(data){
                is ApiResult.Success -> _nasaData.postValue(data.data)
                is ApiResult.Error -> _error.postValue(data.error)
            }
        }
    }

    // Initialize LiveData with cached data on ViewModel creation
    fun initCache() {
        val cachedData = cache.getNASAData()
        if (cachedData != null) {
            _nasaData.postValue(cachedData)
        }
    }

    fun isVideoContent(): Boolean {
        val nasaData = nasaData.value
        return nasaData != null && nasaData.media_type == "video"
    }

}