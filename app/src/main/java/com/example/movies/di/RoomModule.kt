package com.example.movies.di

import android.content.Context
import androidx.room.Room
import com.example.movies.dao.FavouriteMoviesDao
import com.example.movies.database.FavouriteMoviesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): FavouriteMoviesDb {
        return Room.databaseBuilder(
            appContext,
            FavouriteMoviesDb::class.java,
            "favourites_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideChannelDao(database: FavouriteMoviesDb): FavouriteMoviesDao {
        return database.favouriteMoviesDao()
    }
}