package com.example.movies.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.model.MovieDetail
import com.example.movies.apis.MovieApi
import com.example.movies.viewmodel.MovieViewModel
import java.lang.Exception

//PagingSource<page_type, api_response_type>
class MoviePagingSource(val movieApi: MovieApi, val endPoint:String, val viewModel: MovieViewModel) : PagingSource<Int, MovieDetail>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDetail> {
        try {
            val position = params.key ?:1
            val response = when (endPoint) {
                "now_playing" -> movieApi.getNowPlayingMovies(position)
                "popular" -> movieApi.getPopularMovies(position)
                "top_rated" -> movieApi.getTopRatedMovies(position)
                "upcoming" -> movieApi.getUpcomingMovies(position)
                else -> null
            }
            if (response != null) {
                return if (response.isSuccessful) {
                    Log.d("taggg","endpoint : ${endPoint}   response : $response")
                    val moviesResult = response?.body()
                    val movies = moviesResult?.results ?: emptyList()

                    LoadResult.Page(
                        data = movies,
                        prevKey = if (position == 1) null else position - 1,
                        nextKey = if (position == moviesResult?.total_pages) null else position + 1
                    )
                } else {
                    // Handle API error here
                    viewModel.handleMovieFetchError( "Failed to fetch movies, please try again")
                    LoadResult.Error(Exception("Failed to fetch movies, please try again"))
                }
            }
            Log.d("taggg","endpoint000000000000 : ${endPoint}   response : $response")
            return LoadResult.Error(Exception("Failed to fetch movies, please try again"))

        }catch (e:Exception){
            viewModel.handleMovieFetchError( "Failed to fetch movies, please try again")
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDetail>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}