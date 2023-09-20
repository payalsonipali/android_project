package com.example.movies.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movies.apis.MovieApi
import com.example.movies.model.Backdrops
import com.example.movies.model.MovieExtraDetail
import com.example.movies.paging.MoviePagingSource
import com.example.movies.viewmodel.MovieViewModel
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(val movieApi: MovieApi) {

    fun getMoviePage(endPoint:String, viewModel: MovieViewModel, query:String?) = Pager(
        config = PagingConfig(20, 50), //PagingConfig(data_per_page, cached_50_objects)
        pagingSourceFactory = {MoviePagingSource(movieApi, endPoint, viewModel, query)}
    ).flow

    suspend fun getMovieDetailById(id:Long): Response<MovieExtraDetail?> = movieApi.getMovieDetailById(id)
    suspend fun getBackdrops(id:Long): Response<Backdrops?> {
        val result = movieApi.getBackdropsByMovieId(id)
        Log.d("taggg","in repo $id  :  ${result.body()}")
        return result
    }
}