package com.example.movies.view.botoomNavScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movies.model.Movie
import com.example.movies.viewmodel.FavouritesViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Favourites(navHostController:NavHostController) {
    val favouriteViewModel: FavouritesViewModel = hiltViewModel()
    val movies = favouriteViewModel.getAllFavourites().observeAsState().value

    if (movies != null) {
        RecyclerView(movies = movies, navHostController = navHostController)
    }
}

@Composable
fun RecyclerView(movies:List<Movie>, navHostController: NavHostController){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        items(movies.size) { index ->
            ListItem(movies[index], navHostController, true, true)
        }
    }
}