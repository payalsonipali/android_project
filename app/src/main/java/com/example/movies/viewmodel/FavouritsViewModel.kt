package com.example.movies.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movies.dao.FavouriteMoviesDao
import com.example.movies.model.Movie
import com.example.movies.repository.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: FavouriteRepository,
    private val favouriteMoviesDao: FavouriteMoviesDao,
    val sharedPreferences:SharedPreferences
    ) : ViewModel() {
    var isLoading = mutableStateOf(false)

    private val currentUserUid: String
        get() = sharedPreferences.getString("uid", "")!!

    val favoriteIdsFlow: Flow<List<Long>> = favouriteMoviesDao.getFavouriteMoviesId(currentUserUid)

    fun getAllFavourites(): LiveData<List<Movie>?> =  repository.getFavMovieList()

    suspend fun toggleFavoritesIcon(movie: Movie) = repository.toggleFavoritesIcon(movie)

}
