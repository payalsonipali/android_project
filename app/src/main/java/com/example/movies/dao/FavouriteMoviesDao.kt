package com.example.movies.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.entity.Favorite

@Dao
interface FavouriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavourites(favorite: Favorite)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavourites():LiveData<List<Favorite>?>

    @Query("DELETE FROM favorite_movies WHERE id = :movieId")
    fun removeFromFavourites(movieId:Long)

    @Query("SELECT * FROM favorite_movies WHERE id = :movieId LIMIT 1")
    fun getMovieById(movieId: Long):Favorite?
}