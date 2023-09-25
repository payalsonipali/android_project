package com.example.movies.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.entity.Favorite
import com.example.movies.model.Movie
import com.example.movies.model.UserProfile
import com.example.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavourites(favorite: Favorite)

    @Query("SELECT movie FROM favorite_movies LIMIT :limit OFFSET :offset")
    fun getAllFavourites(limit:Int, offset:Int): LiveData<List<Movie>?>

    @Query("SELECT movie FROM favorite_movies")
    fun getList(): LiveData<List<Movie>?>

    @Query("DELETE FROM favorite_movies WHERE id = :movieId")
    fun removeFromFavourites(movieId:Long)

    @Query("SELECT * FROM favorite_movies WHERE id = :movieId LIMIT 1")
    fun getMovieById(movieId: Long):Favorite?

    @Query("SELECT id FROM favorite_movies")
    fun getFavouriteMoviesId(): Flow<List<Long>>

}