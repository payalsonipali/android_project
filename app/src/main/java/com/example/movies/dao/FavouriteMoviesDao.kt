package com.example.movies.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.entity.Favorite
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavourites(favorite: Favorite)

    @Query("SELECT movie FROM favorite_movies LIMIT :limit OFFSET :offset")
    fun getAllFavourites(limit:Int, offset:Int): LiveData<List<Movie>?>

    @Query("SELECT movie FROM favorite_movies WHERE uid = :userId")
    fun getList(userId:String): LiveData<List<Movie>?>

    @Query("DELETE FROM favorite_movies WHERE uid = :userId And id = :movieId")
    fun removeFromFavourites(movieId:Long, userId:String)

    @Query("SELECT * FROM favorite_movies WHERE uid = :userId And id = :movieId LIMIT 1")
    fun getMovieById(movieId: Long, userId:String):Favorite?

    @Query("SELECT id FROM favorite_movies WHERE uid = :userId")
    fun getFavouriteMoviesId(userId:String): Flow<List<Long>>

}