package com.example.movies.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.movies.dao.FavouriteMoviesDao
import com.example.movies.model.Movie
import com.example.movies.paging.FavouritePagingSource
import com.example.movies.viewmodel.FavouritesViewModel
import javax.inject.Inject

class FavouriteRepository @Inject constructor(val favouriteMoviesDao: FavouriteMoviesDao) {

    fun getAllFavourites(favouritesViewModel: FavouritesViewModel): LiveData<PagingData<Movie>> {
        Log.d("taggg", "repo fav calledddddddddddd")
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { FavouritePagingSource(favouriteMoviesDao, favouritesViewModel) }
        ).liveData
    }

    fun getList(): LiveData<List<Movie>?> = favouriteMoviesDao.getList()

}