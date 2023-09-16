package com.payal.digital_movies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.payal.digital_movies.firebase.AnalyticsTracker
import com.payal.digital_movies.model.Content
import com.payal.digital_movies.viewModel.MovieViewModel
import java.lang.Exception

class MoviePageSource(val viewModel: MovieViewModel) : PagingSource<Int, Content>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        try {
            val position = params.key ?:1

            val response = viewModel.loadJsonFromAssets(position)
            if (response != null) {

                AnalyticsTracker().log("Page loaded successfully")

                return LoadResult.Page(
                    data = response,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (position == 3) null else position + 1
                )
            }
            AnalyticsTracker().log("Error : Failed to fetch movies, please try again!")
            return LoadResult.Error(Exception("Failed to fetch movies, please try again"))

        }catch (e: Exception){
            AnalyticsTracker().exception(e)
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}