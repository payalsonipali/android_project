package com.example.movies.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movies.dao.FavouriteMoviesDao
import com.example.movies.entity.Favorite
import com.example.movies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    val favouriteMoviesDao: FavouriteMoviesDao,
    val sharedPreferences: SharedPreferences
) : FavouriteRepository {

    private val currentUserUid: String?
        get() = sharedPreferences.getString("uid", "")

    override fun getFavMovieList(): LiveData<List<Movie>?> {
        try {
            val result = favouriteMoviesDao.getList(currentUserUid!!)
            if (result != null) {
                return result
            }
        } catch (e: Exception) {
            throw Exception("Something went wrong : ${e.message}")
        }
        return MutableLiveData<List<Movie>?>(emptyList())
    }

    override suspend fun toggleFavoritesIcon(movie: Movie) {
        val uid = currentUserUid!!
        try {
            withContext(Dispatchers.IO) {
                val movieId = movie.id
                val getMovieById = favouriteMoviesDao.getMovieById(movieId, uid)
                if (getMovieById == null) {
                    val favorite = Favorite(uid, movieId, movie)
                    favouriteMoviesDao.addToFavourites(favorite)
                } else {
                    favouriteMoviesDao.removeFromFavourites(movieId, uid)
                }
            }
        } catch (e: Exception) {
            throw Exception("something went wrong. Try again later !!")
        }
    }

}