package com.example.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.Favorite
import com.example.movies.repository.FavouritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritsViewModel(private val repository: FavouritesRepository):ViewModel() {

    fun addToFavourites(favourite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavourites(favourite)
        }
    }

    fun getAllFavouriteMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavourites()
        }
    }
}