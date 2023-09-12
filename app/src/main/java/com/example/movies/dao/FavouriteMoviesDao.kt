package com.example.movies.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movies.model.Favorite

@Dao
interface FavouriteMoviesDao {

    @Insert
    suspend fun addToFavourites(favorite: Favorite)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavourites():LiveData<List<Favorite>>
}