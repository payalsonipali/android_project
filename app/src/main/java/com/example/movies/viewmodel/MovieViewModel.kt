package com.example.movies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.model.MovieDetail
import com.example.movies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val moviewRepository: MovieRepository) : ViewModel() {

    private val currentEndpointFlow = MutableStateFlow("now_playing")

    val currentEndpoint: Flow<String> get() = currentEndpointFlow
    private val _errorLiveData = MutableLiveData<String>()

    val errorLiveData: LiveData<String> get() = _errorLiveData

    val moviePagerFlow: Flow<PagingData<MovieDetail>> = currentEndpointFlow
        .flatMapLatest { endpoint ->
            moviewRepository.getMoviePage(endpoint, this)
        }
        .cachedIn(viewModelScope)

    fun setEndpoint(endpoint: String) {
        currentEndpointFlow.value = endpoint
    }

    fun handleMovieFetchError(errorMessage: String) {
        _errorLiveData.value = errorMessage
    }

    fun clearError() {
        Log.d("taggg", "error")
        _errorLiveData.postValue("")
    }

    fun getNowPlayingMovies() {
        Log.d("taggg", "get playing now")
        setEndpoint("now_playing")
    }

    fun getPopularMovies() {
        Log.d("taggg", "get popular")
        setEndpoint("popular")
    }

    fun getTopRatedMovies() {
        setEndpoint("top_rated")
    }

    fun getUpcomingMovies() {
        setEndpoint("upcoming")
    }
}