package com.example.movies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movies.dao.FavouriteMoviesDao
import com.example.movies.entity.CombinedTypeConverter
import com.example.movies.entity.Favorite

@Database(entities = [Favorite::class], version = 2, exportSchema = false)
@TypeConverters(CombinedTypeConverter::class)
abstract class FavouriteMoviesDb : RoomDatabase() {
    abstract fun favouriteMoviesDao(): FavouriteMoviesDao
}