package com.example.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movies.dao.FavouriteMoviesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val favouriteMoviesDao: FavouriteMoviesDao) : ViewModel() {
//    val favoriteIds: LiveData<List<Long>> by lazy {
//        liveData {
//            favouriteMoviesDao.getFavouriteMoviesId()?.let { emit(it) }
//        }
//    }
}
