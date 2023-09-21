package com.example.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movies.dao.FavouriteMoviesDao
import com.example.movies.entity.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val favouriteMoviesDao: FavouriteMoviesDao) : ViewModel() {
    suspend fun addToFavourites(favorite: Favorite) {
        favouriteMoviesDao.addToFavourites(favorite)
    }

    fun getAllFavourites(): LiveData<List<Favorite>?> {
        val result = favouriteMoviesDao.getAllFavourites()
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
