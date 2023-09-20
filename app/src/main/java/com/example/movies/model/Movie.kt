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
):Parcelable

@Parcelize
data class MovieExtraDetail(
    val genres: List<Genre>?
):Parcelable

@Parcelize
data class Genre(
    val id: Long,
    val name: String?
):Parcelable

@Parcelize
data class MovieAllDetail(
    val movie:Movie,
    val movieExtraDetail: MovieExtraDetail?
):Parcelable

@Parcelize
data class Backdrops(
    val backdrops:List<Backdrop>
):Parcelable

@Parcelize
data class Backdrop(
    val file_path: String,
):Parcelable