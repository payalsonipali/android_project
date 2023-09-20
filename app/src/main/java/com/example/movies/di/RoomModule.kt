package com.example.movies.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
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
class RoomModule {

    @Singleton
    @Provides
    fun provideChannelDao(favouriteMoviesDb: FavouriteMoviesDb): FavouriteMoviesDao {
        return favouriteMoviesDb.favouriteMoviesDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): RoomDatabase {
        return Room.databaseBuilder(
            appContext,
            RoomDatabase::class.java,
            "favourites_db"
        ).build()
    }

}