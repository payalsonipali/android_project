package com.example.movies.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movies.apis.MovieApi
import com.example.movies.model.Backdrop
import com.example.movies.model.Genres
import com.example.movies.paging.MoviePagingSource
import com.example.movies.utils.Resource
import com.example.movies.viewmodel.MovieViewModel
import java.lang.Exception
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(val movieApi: MovieApi) : MovieRepository{

    override fun getMoviePage(endPoint:String, viewModel: MovieViewModel, query:String?) = Pager(
        config = PagingConfig(20, 50), //PagingConfig(data_per_page, cached_50_objects)
        pagingSourceFactory = { MoviePagingSource(movieApi, endPoint, viewModel, query) }
    ).flow

    override suspend fun getMovieDetailById(id:Long): Resource<Genres?> {
        try{
            val result = movieApi.getMovieDetailById(id)
            val body = result.body()
            if(result.isSuccessful && body != null){
                return Resource.Success(body)
            }
        } catch (e:Exception){
            return Resource.Error("Something went wrong : ${e.message}")
        }
        return Resource.Error("No genres available !!")
    }

    override suspend fun getBackdrops(id:Long): Resource<List<Backdrop>> {
        try{
            val result = movieApi.getBackdropsByMovieId(id)
            val body = result.body()
            if(result.isSuccessful && body != null){
                return Resource.Success(body.backdrops)
            }
        } catch (e:Exception){
            return Resource.Error("Something went wrong : ${e.message}")
        }
        return Resource.Error("No backdrops available !!")
    }

    override suspend fun getTrailer(movieId:Long): Resource<String?> {
        try{
            val result = movieApi.getMovieTrailerById(movieId)
            val body = result.body()
            if(result.isSuccessful && body != null){
                val firstTrailer = body.results.find { it.type == "Trailer" }
                if (firstTrailer != null) {
                    return Resource.Success(firstTrailer.key)
                }
            }
        } catch (e:Exception){
            return Resource.Error("Something went wrong : ${e.message}")
        }
        return Resource.Error("No trailer available !!")
    }

}