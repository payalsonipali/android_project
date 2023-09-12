package com.example.movies.apis

import com.example.movies.model.MoviesResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page:Int): Response<MoviesResult>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("popular")
    suspend fun getPopularMovies(@Query("page") page:Int): Response<MoviesResult>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("top_rated")
    suspend fun getTopRatedMovies(@Query("page") page:Int): Response<MoviesResult>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("upcoming")
    suspend fun getUpcomingMovies(@Query("page") page:Int): Response<MoviesResult>
}