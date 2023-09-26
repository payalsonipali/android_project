package com.example.movies.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.model.Backdrops
import com.example.movies.model.Movie
import com.example.movies.model.Genres
import com.example.movies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val moviewRepository: MovieRepository) : ViewModel() {
    var selectedItem: String? by mutableStateOf(null)

    var isLoading = mutableStateOf(false)

    private val currentEndpointFlow = MutableStateFlow(EndpointWithParameter("now_playing", null))

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private val _genres = MutableLiveData<Genres?>()

    private val _refreshFlag = MutableStateFlow(false)

    val refreshFlag = _refreshFlag.asStateFlow()

    fun setRefreshFlag(value: Boolean) {
        _refreshFlag.value = value
    }

    val moviePagerFlow: Flow<PagingData<Movie>> = currentEndpointFlow
        .flatMapLatest { (endpoint, parameter) ->
            moviewRepository.getMoviePage(endpoint, this, parameter)
        }
        .cachedIn(viewModelScope)

    fun setEndpoint(endpoint: String, query: String?) {
        currentEndpointFlow.value = EndpointWithParameter(endpoint, query)
    }

    suspend fun getBackdrops(id: Long): Backdrops? {
        return try {
            val response = moviewRepository.getBackdrops(id)
            val body = response.body()
            if (response.isSuccessful) {
                body
            } else {
                // Handle API error here
                null
            }
        } catch (e: Exception) {
            // Handle network or other exceptions
            null
        }
    }

    suspend fun getTrailer(movieId:Long): String? {
        try {
            val response = moviewRepository.getTrailer(movieId)
            val body = response.body()
            Log.d("taggg","body : $body")
            if(response.isSuccessful && body != null){
                val firstTrailer = body.results.find { it.type == "Trailer" }
                if (firstTrailer != null) {
                    Log.d("taggg","key : ${firstTrailer.key}")
                    return firstTrailer.key
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun getMovieDetailById(id: Long): Genres? {
        return try {
            val response = moviewRepository.getMovieDetailById(id)
            val body = response.body()
            if (response.isSuccessful) {
                body
            } else {
                // Handle API error here
                null
            }
        } catch (e: Exception) {
            // Handle network or other exceptions
            null
        }
    }

    fun handleMovieFetchError(errorMessage: String) {
        _errorLiveData.value = errorMessage
    }

    fun clearError() {
        Log.d("taggg", "error")
        _errorLiveData.postValue("")
    }
}

data class EndpointWithParameter(val endpoint: String, val parameter: String?)
