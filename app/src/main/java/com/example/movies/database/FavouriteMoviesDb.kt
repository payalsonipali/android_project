package com.example.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.dao.FavouriteMoviesDao
import com.example.movies.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavouriteMoviesDb:RoomDatabase() {
    abstract fun favouriteMoviesDao():FavouriteMoviesDao

    companion object {
        private var instance: FavouriteMoviesDb? = null
        fun getInstance(context: Context): FavouriteMoviesDb {
            if (instance == null) {
                synchronized(FavouriteMoviesDb::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavouriteMoviesDb::class.java,
                        "favourites_db"
                    ).build()
                }
            }
            return instance!!
        }
    }
}