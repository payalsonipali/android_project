package com.example.movies.repository

import androidx.lifecycle.LiveData
import com.example.movies.dao.FavouriteMoviesDao
import com.example.movies.model.Favorite

class FavouritesRepository(private val favouriteMoviesDao: FavouriteMoviesDao) {

    suspend fun addToFavourites(favorite: Favorite){
        return favouriteMoviesDao.addToFavourites(favorite)
    }

    suspend fun getAllFavourites(): LiveData<List<Favorite>> {
        return favouriteMoviesDao.getAllFavourites()
    }
}