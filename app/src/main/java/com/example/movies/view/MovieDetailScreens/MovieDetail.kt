package com.example.movies.view.MovieDetailScreens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movies.Constants.IMAGE_BASE_URL
import com.example.movies.R
import com.example.movies.model.Movie
import com.example.movies.ui.theme.grey
import com.example.movies.utils.Resource
import com.example.movies.viewmodel.FavouritesViewModel
import com.example.movies.viewmodel.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetail(movie: Movie, isInFav: Boolean, navHostController: NavHostController) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(grey)
        ) {
            item {
                ImageContainer(navHostController, movie, isInFav)
            }
            item {
                MovieContent(movie = movie, isOnDetailScreen = true)
            }
        }
}

@Composable
fun ImageContainer(navHostController: NavHostController, movie: Movie, isInFav: Boolean) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    val favouritsViewModel: FavouritesViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(isInFav) } // Initialize with the initial favorite status
    val context = LocalContext.current

    Box {
        Image(
            painter = rememberImagePainter(IMAGE_BASE_URL + movie.poster_path),
            contentDescription = "movie_img",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    coroutineScope.launch {
                        var backdrops = movie.backdrops
                        if (!isInFav) {
                            val result = movieViewModel.getBackdrops(movie.id)
                            if (result is Resource.Success) {
                                backdrops = result.data
                            }

                            if (result is Resource.Error) {
                                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                            }
                        }

                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            "backdrops",
                            backdrops
                        )
                        navHostController.navigate("backdrops")
                    }
                },
            contentScale = ContentScale.FillBounds
        )

        Icon(
            painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .clickable {
                    navHostController.popBackStack()
                }
        )

        Icon(
            painterResource(id = R.drawable.youtube),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(50.dp)
                .padding(end = 10.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    coroutineScope.launch {
                        val trailer = movieViewModel.getTrailer(movie.id)
                        if(trailer is Resource.Success){
                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                "trailer",
                                trailer.data
                            )
                            navHostController.navigate("trailer")
                        }

                        if(trailer is Resource.Error){
                            Toast.makeText(context, "${trailer.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        )

        Icon(
            painterResource(id = if (!isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopEnd)
                .clickable {
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            val backdrops = movieViewModel.getBackdrops(movie.id)
                            if (backdrops is Resource.Success){
                                val updatedMovie = backdrops.data?.let { movie.withBackdrops(it) } ?: movie
                                favouritsViewModel.toggleFavoritesIcon(updatedMovie)
                                isFavorite = !isFavorite
                            }
                        }
                    }

                }
        )

    }
}
