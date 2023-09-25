package com.example.movies.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movies.dao.FavouriteMoviesDao
import com.example.movies.entity.Favorite
import com.example.movies.model.Movie
import com.example.movies.repository.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repository: FavouriteRepository,private val favouriteMoviesDao: FavouriteMoviesDao) : ViewModel() {
    var isLoading = mutableStateOf(false)

    val favoriteIdsFlow: Flow<List<Long>> = favouriteMoviesDao.getFavouriteMoviesId()

    fun getAllFavourites(): LiveData<List<Movie>?> {
        val result = repository.getList()
        return result
    }

    suspend fun toggleFavoritesIcon(favorite: Favorite){
        withContext(Dispatchers.IO) {
            val movieId = favorite.movie.id
            val getMovieById = favouriteMoviesDao.getMovieById(movieId)
            if(getMovieById == null){
                favouriteMoviesDao.addToFavourites(favorite)
            } else{
                favouriteMoviesDao.removeFromFavourites(movieId)
            }
        }
    }
}
