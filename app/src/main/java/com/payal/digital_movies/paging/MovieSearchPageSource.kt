package com.payal.digital_movies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.payal.digital_movies.firebase.AnalyticsTracker
import com.payal.digital_movies.model.Content
import com.payal.digital_movies.viewModel.MovieViewModel

class MovieSearchPageSource(
    val viewModel: MovieViewModel,
    val query: String
) : PagingSource<Int, Content>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {

        try {
            val position = params.key ?: 1

            val response = viewModel.loadJsonFromAssets(position)
            if (response != null) {
                // Filter the content based on the search query
                val filteredContent = response.filter { content ->
                    content.name.contains(query, ignoreCase = true)
                }
                AnalyticsTracker().log("Page loaded successfully")
                return LoadResult.Page(
                    data = filteredContent,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (position == 3) null else position + 1
                )
            }
            AnalyticsTracker().log("Error : Failed to fetch movies, please try again")
            return LoadResult.Error(Exception("Failed to fetch movies, please try again"))

        } catch (e: Exception) {
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
