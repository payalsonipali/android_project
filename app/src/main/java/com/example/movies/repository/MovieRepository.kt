package com.example.movies.repository

import androidx.paging.PagingData
import com.example.movies.model.Backdrop
import com.example.movies.model.Genres
import com.example.movies.model.Movie
import com.example.movies.utils.Resource
import com.example.movies.viewmodel.MovieViewModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository{

    fun getMoviePage(endPoint:String, viewModel: MovieViewModel, query:String?) : Flow<PagingData<Movie>>

    suspend fun getMovieDetailById(id:Long): Resource<Genres?>

    suspend fun getBackdrops(id:Long): Resource<List<Backdrop>>

    suspend fun getTrailer(movieId:Long): Resource<String?>
}