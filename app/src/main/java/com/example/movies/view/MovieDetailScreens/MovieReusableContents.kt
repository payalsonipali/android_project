package com.example.movies.view.MovieDetailScreens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movies.Constants
import com.example.movies.R
import com.example.movies.model.Genre
import com.example.movies.model.Movie
import com.example.movies.model.MovieWithIsScreenFav
import com.example.movies.ui.theme.grey
import com.example.movies.ui.theme.light_grey
import com.example.movies.utils.Resource
import com.example.movies.viewmodel.FavouritesViewModel
import com.example.movies.viewmodel.MovieViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun ListItem(movie: Movie, navHostController: NavHostController, isOnFavoriteScreen: Boolean) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .height(260.dp)
            .padding(10.dp)
            .clickable {

                var updatedMovie = MovieWithIsScreenFav(movie, true)
                if (!isOnFavoriteScreen) {
                    val result = runBlocking { movieViewModel.getMovieDetailById(movie.id) }
                    if (result is Resource.Success) {
                        updatedMovie =
                            MovieWithIsScreenFav(
                                movie.withUpdatedGenres(result.data?.genres),
                                false
                            )
                    }

                    if (result is Resource.Error) {
                        Toast.makeText(context, "${result.message}", Toast.LENGTH_LONG).show()
                    }
                }

                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "movieDetail",
                    updatedMovie
                )

                navHostController.navigate("movie_detail")
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {
            Image(
                painter = rememberImagePainter(Constants.IMAGE_BASE_URL + movie.poster_path),
                contentDescription = "movie_img",
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentScale = ContentScale.Crop
            )
            MovieContent(movie, false)
        }
    }

}

@Composable
fun MovieContent(movie: Movie, isOnDetailScreen: Boolean) {
    Column(
        modifier = modifier(isOnDetailScreen),
    ) {
        if (isOnDetailScreen) {
            movie.genres?.let { RowGeners(it) }
        }
        RowTitleAndFav(movie.original_title, movie.release_date, movie.id)
        RatingBar(movie.vote_average, 10f)
        MovieOverView(isOnDetailScreen, movie.overview)
    }
}

fun modifier(isOnDetailScreen: Boolean): Modifier {
    val modifier: Modifier
    modifier = if (isOnDetailScreen) {
        Modifier
            .background(grey)
            .fillMaxHeight()
            .padding(start = 10.dp, end = 10.dp, bottom = 8.dp, top = 5.dp)
            .fillMaxWidth()
    } else {
        Modifier
            .background(grey)
            .padding(start = 10.dp, end = 10.dp, bottom = 8.dp, top = 5.dp)
            .fillMaxWidth()
    }
    return modifier
}

@Composable
fun RowGeners(geners: List<Genre>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        geners.forEach { item ->
            item {
                Card(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(25.dp)),
                    colors = CardDefaults.cardColors(light_grey),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = item.name ?: "",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = Color.White
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun RowTitleAndFav(title: String, releaseDate: String, id: Long) {
    // Observe changes in favoriteIds using observeAsState
    val favouritesViewModel: FavouritesViewModel = hiltViewModel()
    val favoriteIds by favouritesViewModel.favoriteIdsFlow.collectAsState(emptyList())
    val isFavorite = favoriteIds.contains(id)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp), // Padding for the Row
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$title (${releaseDate.takeIf { it.length >= 4 }?.substring(0, 4)})",
            maxLines = 3,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f) // Take available space
                .padding(end = 8.dp),
        )
        Spacer(modifier = Modifier.width(10.dp)) // Spacer for icon

        if (isFavorite == true) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.LightGray, // Change the icon color as needed
            )
        }
    }
}

@Composable
fun RatingBar(rating: Float, maxRating: Float = 10f) {
    val numberOfStars = 5
    val fullStars = (rating / maxRating * numberOfStars).toInt()
    val hasHalfStar = (rating % 1) >= 0.5f

    Row() {
        repeat(fullStars) {
            Icon(
                painterResource(id = R.drawable.baseline_star_24),
                tint = Color.Yellow,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 2.dp)

            )
        }

        if (hasHalfStar) {
            Icon(
                painterResource(id = R.drawable.baseline_star_half_24),
                tint = Color.Yellow,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 2.dp)
            )
        }

        val remainingStars = numberOfStars - fullStars - if (hasHalfStar) 1 else 0

        repeat(remainingStars) {
            Icon(
                painterResource(id = R.drawable.baseline_star_outline_24),
                tint = Color.Yellow,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 2.dp)
            )
        }
    }
}

@Composable
fun MovieOverView(isOnDetailScreen: Boolean, overview: String) {
    if (isOnDetailScreen) {
        Text(
            text = overview,
            fontSize = 12.sp,
            color = light_grey,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 15.sp
        )
    } else {
        Text(
            text = overview,
            fontSize = 12.sp,
            color = light_grey,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 15.sp
        )
    }
}