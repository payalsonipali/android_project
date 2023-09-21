package com.example.movies.view.botoomNavScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.movies.Constants
import com.example.movies.viewmodel.FavouritesViewModel

@Composable
fun Favourites() {
    val favouritsViewModel: FavouritesViewModel = hiltViewModel()
    val lazyListState = rememberLazyGridState()

    val favMovies = favouritsViewModel.getAllFavourites().observeAsState()
    val movies = favMovies.value

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Two columns per row
        content = {
            if (movies != null) {
                items(movies.size) { index ->
                    Image(
                        painter = rememberImagePainter(Constants.IMAGE_BASE_URL + movies[index].movie.poster_path),
                        contentDescription = "movie_img",
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    )
}