package com.example.movies.di

import android.content.Context
import android.content.SharedPreferences
import com.example.movies.Constants.BASE_URL
import com.example.movies.apis.MovieApi
import com.example.movies.repository.AuthRepository
import com.example.movies.repository.AuthRepositoryImpl
import com.example.movies.repository.ProfileRepository
import com.example.movies.repository.ProfileRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun getMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFirebaseInstance(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provieFirebaseFirestoreInstance() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provieRepositoryImpl(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore, firbaseStorage: FirebaseStorage, sharedPreferences: SharedPreferences): AuthRepository{
        return AuthRepositoryImpl(firebaseAuth, firebaseFirestore, firbaseStorage, sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provieProfileRepositoryImpl(firebaseFirestore: FirebaseFirestore, sharedPreferences: SharedPreferences): ProfileRepository{
        return ProfileRepositoryImpl(firebaseFirestore, sharedPreferences)
    }
}