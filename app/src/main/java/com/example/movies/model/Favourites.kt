package com.example.movies.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val title:String,
    val overview:String,
    val poster_path:String,
    val release_date:String,
    val vote_average:Float
) {
}