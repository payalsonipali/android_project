package com.example.movies.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movies.apis.MovieApi
import com.example.movies.paging.MoviePagingSource
import com.example.movies.viewmodel.MovieViewModel
import javax.inject.Inject

class MovieRepository @Inject constructor(val movieApi: MovieApi) {

    fun getMoviePage(endPoint:String, viewModel:MovieViewModel) = Pager(
        config = PagingConfig(20, 50), //PagingConfig(data_per_page, cached_50_objects)
        pagingSourceFactory = {MoviePagingSource(movieApi, endPoint, viewModel)}
    ).flow
}