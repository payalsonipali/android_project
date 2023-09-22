package com.example.movies.apis

import com.example.movies.model.Backdrops
import com.example.movies.model.Genres
import com.example.movies.model.MoviesResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page:Int): Response<MoviesResult>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page:Int): Response<MoviesResult>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page:Int): Response<MoviesResult>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page:Int): Response<MoviesResult>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("search/movie")
    suspend fun getMoviesByName(@Query("query") query:String, @Query("page") page:Int): Response<MoviesResult>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("movie/{movieId}")
    suspend fun getMovieDetailById(@Path("movieId") movieId: Long): Response<Genres?>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics")
    @GET("movie/{movieId}/images")
    suspend fun getBackdropsByMovieId(@Path("movieId") movieId: Long): Response<Backdrops?>
}