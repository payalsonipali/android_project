package com.payal.weatherapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecyclerViewModel @Inject constructor(val repository: Repository) : ViewModel(){

    private val _weatherData = MutableLiveData<List<Forecastday>?>()
    val weatherData: LiveData<List<Forecastday>?> get() = _weatherData

    fun getWeatherData(location: String, date: String) {
        viewModelScope.launch {
            try {
                val data = repository.getWheatherHistory(location, "2023-11-21")
                _weatherData.postValue(data)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}