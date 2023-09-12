package com.example.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.model.Favorite
import com.example.movies.model.MovieDetail
import com.example.movies.repository.FavouritesRepository
import com.example.movies.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MovieViewModel(val moviewRepository: MovieRepository) : ViewModel() {

    private val currentEndpointFlow = MutableStateFlow("now_playing")

    val currentEndpoint: Flow<String> get() = currentEndpointFlow
    private val _errorLiveData = MutableLiveData<String>()

    val errorLiveData: LiveData<String> get() = _errorLiveData

    val moviePagerFlow: Flow<PagingData<MovieDetail>> = currentEndpointFlow
        // Ensure changes in the endpoint trigger a reload
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
        _errorLiveData.postValue("")
    }

    fun getNowPlayingMovies() {
        setEndpoint("now_playing")
    }

    fun getPopularMovies() {
        setEndpoint("popular")
    }

    fun getTopRatedMovies() {
        setEndpoint("top_rated")
    }

    fun getUpcomingMovies() {
        setEndpoint("upcoming")
    }
}