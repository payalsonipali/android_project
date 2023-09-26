package com.example.movies.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.model.Backdrop
import com.example.movies.model.BackdropState
import com.example.movies.model.Movie
import com.example.movies.model.Genres
import com.example.movies.repository.MovieRepository
import com.example.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val moviewRepository: MovieRepository) : ViewModel() {
    var selectedItem: String? by mutableStateOf(null)

    var isLoading = mutableStateOf(false)

    private val currentEndpointFlow = MutableStateFlow(EndpointWithParameter("now_playing", null))

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    val moviePagerFlow: Flow<PagingData<Movie>> = currentEndpointFlow
        .flatMapLatest { (endpoint, parameter) ->
            moviewRepository.getMoviePage(endpoint, this, parameter)
        }
        .cachedIn(viewModelScope)

    fun setEndpoint(endpoint: String, query: String?) {
        currentEndpointFlow.value = EndpointWithParameter(endpoint, query)
    }

    val _backdropState = mutableStateOf(BackdropState())
    val backdropState: State<BackdropState> = _backdropState

    suspend fun getBackdrops(movieId: Long): Resource<List<Backdrop>>  =  moviewRepository.getBackdrops(movieId)

    suspend fun getTrailer(movieId: Long): Resource<String?> = moviewRepository.getTrailer(movieId)

    suspend fun getMovieDetailById(id: Long): Resource<Genres?> = moviewRepository.getMovieDetailById(id)

    fun handleMovieFetchError(errorMessage: String) {
        _errorLiveData.value = errorMessage
    }

    fun clearError() {
        _errorLiveData.postValue("")
    }
}

data class EndpointWithParameter(val endpoint: String, val parameter: String?)
