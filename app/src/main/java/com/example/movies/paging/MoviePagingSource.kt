package com.example.movies.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.model.Movie
import com.example.movies.apis.MovieApi
import com.example.movies.viewmodel.MovieViewModel
import java.lang.Exception

//PagingSource<page_type, api_response_type>
class MoviePagingSource(val movieApi: MovieApi, val endPoint:String, val viewModel: MovieViewModel, val query:String?) : PagingSource<Int, Movie>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        Log.d("taggg", "paging calleddddddd0000000000000000")

        try {

            viewModel.isLoading.value = true

            val position = params.key ?:1
            Log.d("taggg", "paging called111111111111111111")

            val response =
                if(!query.isNullOrEmpty()){
                    Log.d("taggg", "paging query not null")
                    movieApi.getMoviesByName(query, position)
                } else{
                    when (endPoint) {
                    "now_playing" -> movieApi.getNowPlayingMovies(position)
                    "popular" -> movieApi.getPopularMovies(position)
                    "top_rated" -> movieApi.getTopRatedMovies(position)
                    "upcoming" -> movieApi.getUpcomingMovies(position)
                    else -> null
                }
            }

            if (response != null) {
                return if (response.isSuccessful) {
                    Log.d("taggg","endpoint : ${endPoint}   response : $response")
                    val moviesResult = response?.body()
                    Log.d("taggg","movieResult  : $moviesResult")
                    val movies = moviesResult?.results ?: emptyList()
                    Log.d("taggg","movies : $movies")

                    viewModel.isLoading.value = false

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
            return LoadResult.Error(Exception("Failed to fetch movies, please try again"))

        }catch (e:Exception){
            viewModel.handleMovieFetchError( "Failed to fetch movies, please try again")
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