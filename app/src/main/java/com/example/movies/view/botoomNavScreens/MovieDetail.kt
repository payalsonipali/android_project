package com.example.movies.view.botoomNavScreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movies.Constants.IMAGE_BASE_URL
import com.example.movies.R
import com.example.movies.database.FavouriteMoviesDb
import com.example.movies.entity.Favorite
import com.example.movies.model.Movie
import com.example.movies.model.MovieAllDetail
import com.example.movies.viewmodel.FavouritesViewModel
import com.example.movies.viewmodel.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetail(movie: Movie, isInFav:Boolean, navHostController: NavHostController) {
    Log.d("taggg", "Movie detail called $movie : $isInFav")
    Scaffold(
        content = {
            Column(
            ) {
                ImageContainer(navHostController, movie, isInFav)
                MovieContent(movie = movie, isOnDetailScreen = true,false)
            }
        }
    )
}

@Composable
fun ImageContainer(navHostController: NavHostController, movie: Movie, isInFav: Boolean) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    val favouritsViewModel: FavouritesViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()


    Box {
        Image(
            painter = rememberImagePainter(IMAGE_BASE_URL + movie.poster_path),
            contentDescription = "movie_img",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    var backdrops = movie.backdrops
                    if(!isInFav){
                        backdrops = runBlocking {
                            movieViewModel.getBackdrops(movie.id)
                        }?.backdrops

                        Log.d("taggg","backdropdsssss passed : $backdrops")
                    }

                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                        "backdrops",
                        backdrops
                    )

                    navHostController.navigate("backdrops")
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
        )

        Icon(
            painterResource(id = R.drawable.baseline_favorite_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .clickable {
                    coroutineScope.launch  {
                        withContext(Dispatchers.IO) {
                            val backdrops = movieViewModel.getBackdrops(movie.id)?.backdrops
                            val updatedMovie = backdrops?.let { movie.withBackdrops(it) }?:movie
                            val favorite = Favorite(movie.id,updatedMovie)
                            favouritsViewModel.toggleFavoritesIcon(favorite)
                        }
                    }

                }
        )
    }
}