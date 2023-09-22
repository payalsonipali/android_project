package com.example.movies.paging

import android.util.Log
import android.view.animation.Transformation
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.dao.FavouriteMoviesDao
import com.example.movies.entity.Favorite
import com.example.movies.model.Movie
import com.example.movies.viewmodel.FavouritesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Exception

class FavouritePagingSource(
    val favouriteMoviesDao: FavouriteMoviesDao,
    val favouritesViewModel: FavouritesViewModel
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            Log.d("taggg","paging 00000000000000000000000000")

            val page = params.key ?: 0 // Initial load, start from the first page
            Log.d("taggg","favourites favourites favourites :$page")

            val limit = 10
            val offset = page * 10
            Log.d("taggg","favourites favourites favourites :0000000")

            val favourites = withContext(Dispatchers.IO) {
                favouriteMoviesDao.getAllFavourites(limit, offset)
            }.value

            Log.d("taggg","favourites favourites favourites :$favourites")

            if (favourites != null) {
                Log.d("taggg","its not null")
                favouritesViewModel.isLoading.value = false
                LoadResult.Page(
                    data = favourites,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (favourites.isEmpty()) null else page + 1
                )
            } else {
                Log.d("taggg","its  null so inside elseeeeeee")

                // Handle API error here
//                    favouritesViewModel.handleMovieFetchError("Failed to fetch movies, please try again")
                LoadResult.Error(Exception("Failed to fetch movies, please try again"))
            }
            return LoadResult.Error(Exception("Failed to fetch movies, please try again"))

        } catch (e: Exception) {
//            viewModel.handleMovieFetchError("Failed to fetch movies, please try again")
            Log.d("taggg","some exception occured $e")
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}