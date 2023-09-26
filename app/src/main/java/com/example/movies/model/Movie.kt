package com.example.movies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id:Long,
    var original_title:String,
    var overview:String,
    var poster_path:String,
    var release_date:String,
    var vote_average:Float,
    val genres: List<Genre>?,
    val backdrops:List<Backdrop>?
):Parcelable{
    fun withUpdatedGenres(newGenres: List<Genre>?): Movie {
        return copy(genres = newGenres)
    }
    fun withBackdrops(backdrops: List<Backdrop>): Movie {
        return copy(backdrops = backdrops)
    }
}

@Parcelize
data class Genres(
    val genres: List<Genre>?
):Parcelable

@Parcelize
data class Genre(
    val id: Long,
    val name: String?
):Parcelable

@Parcelize
data class MovieWithIsScreenFav(
    val movie:Movie,
    val isOnFavouriteScreen: Boolean
):Parcelable

@Parcelize
data class MovieAllDetail(
    val movie:Movie,
    val genres: List<Genre>?
):Parcelable

@Parcelize
data class Backdrops(
    val backdrops:List<Backdrop>
):Parcelable

@Parcelize
data class Backdrop(
    val file_path: String,
):Parcelable

@Parcelize
data class Trailer(
    val results: List<TrailerVideo>,
):Parcelable

@Parcelize
data class TrailerVideo(
    val type:String,
    val key:String
):Parcelable

data class BackdropState(
    val isLoading: Boolean = false,
    val success: List<Backdrop>? = null,
    val error: String = ""
)