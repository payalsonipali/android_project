package com.example.movies.repository

import androidx.lifecycle.LiveData
import com.example.movies.model.Movie

interface FavouriteRepository {
    fun getFavMovieList(): LiveData<List<Movie>?>
    suspend fun toggleFavoritesIcon(movie: Movie)
}