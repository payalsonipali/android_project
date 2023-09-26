package com.example.movies.apis

import com.example.movies.model.Backdrops
import com.example.movies.model.Genres
import com.example.movies.model.MoviesResult
import com.example.movies.model.Trailer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page:Int): Response<MoviesResult>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page:Int): Response<MoviesResult>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page:Int): Response<MoviesResult>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page:Int): Response<MoviesResult>

    @GET("search/movie")
    suspend fun getMoviesByName(@Query("query") query:String, @Query("page") page:Int): Response<MoviesResult>

    @GET("movie/{movieId}")
    suspend fun getMovieDetailById(@Path("movieId") movieId: Long): Response<Genres?>

    @GET("movie/{movieId}/images")
    suspend fun getBackdropsByMovieId(@Path("movieId") movieId: Long): Response<Backdrops?>

    @GET("movie/{movieId}/videos")
    suspend fun getMovieTrailerById(@Path("movieId") movieId: Long): Response<Trailer?>
}