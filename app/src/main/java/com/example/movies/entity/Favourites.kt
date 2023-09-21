package com.example.movies.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.movies.model.Backdrops
import com.example.movies.model.Genre
import com.example.movies.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "favorite_movies")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val movie: Movie,
    val genres: List<Genre>?,
    val backdrops: Backdrops?
) {
}


class CombinedTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromMovie(movie: Movie?): String? {
        return gson.toJson(movie)
    }

    @TypeConverter
    fun toMovie(json: String?): Movie? {
        return gson.fromJson(json, Movie::class.java)
    }

    @TypeConverter
    fun fromBackdrops(backdrops: Backdrops?): String? {
        return gson.toJson(backdrops)
    }

    @TypeConverter
    fun toBackdrops(json: String?): Backdrops? {
        return gson.fromJson(json, Backdrops::class.java)
    }

    @TypeConverter
    fun fromGenres(genres: List<Genre>?): String? {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun toGenres(json: String?): List<Genre>? {
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(json, type)
    }
}