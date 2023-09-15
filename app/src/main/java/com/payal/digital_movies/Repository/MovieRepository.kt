package com.payal.digital_movies.Repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.payal.digital_movies.model.Content
import com.payal.digital_movies.paging.MoviePageSource
import com.payal.digital_movies.paging.MovieSearchPageSource
import com.payal.digital_movies.viewModel.MovieViewModel

class MovieRepository {

    fun getMoviePage(viewModel: MovieViewModel) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false // Ensure you don't use placeholders
        ),
        pagingSourceFactory = { MoviePageSource(viewModel = viewModel) }
    ).liveData

    fun getSearchedMovies(viewModel: MovieViewModel, query:String): LiveData<PagingData<Content>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false // Ensure you don't use placeholders
            ),
            pagingSourceFactory = { MovieSearchPageSource(viewModel = viewModel, query =query) }
        ).liveData

    }

}