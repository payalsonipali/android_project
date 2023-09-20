package com.example.movies.view.botoomNavScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies.R
import com.example.movies.model.Genre
import com.example.movies.model.Movie
import com.example.movies.model.MovieAllDetail
import com.example.movies.ui.theme.grey
import com.example.movies.ui.theme.light_grey

@Composable
fun MovieContent(movieAllDetail: MovieAllDetail, isOnDetailScreen:Boolean) {
    val movie = movieAllDetail.movie
    Column(
        modifier = modifier(isOnDetailScreen),
    ) {
        movieAllDetail.movieExtraDetail?.genres?.let { RowGeners(it) }
        RowTitleAndFav(movie.original_title, movie.release_date)
        RatingBar(movie.vote_average, 10f)
        MovieOverView(isOnDetailScreen, movie.overview)
    }
}

fun modifier(isOnDetailScreen:Boolean): Modifier {
    val modifier:Modifier
    modifier = if(isOnDetailScreen){
        Modifier
            .background(grey)
            .fillMaxHeight()
            .padding(start = 10.dp, end = 10.dp, bottom = 8.dp, top = 5.dp)
            .fillMaxWidth()
    } else{
        Modifier
            .background(grey)
            .padding(start = 10.dp, end = 10.dp, bottom = 8.dp, top = 5.dp)
            .fillMaxWidth()
    }
    return modifier
}

@Composable
fun RowGeners(geners:List<Genre>){
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
                            text = item.name?:"",
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
fun RowTitleAndFav(title: String, releaseDate: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp), // Padding for the Row
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$title (${releaseDate.takeIf { it.length >= 4 }?.substring(0,4)})",
            maxLines = 3,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f) // Take available space
                .padding(end = 8.dp),
        )
        Spacer(modifier = Modifier.width(10.dp)) // Spacer for icon

        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = Color.LightGray, // Change the icon color as needed
        )
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
fun MovieOverView(isOnDetailScreen:Boolean, overview:String){
    if(isOnDetailScreen){
        Text(
            text = overview,
            fontSize = 12.sp,
            color = light_grey,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 15.sp
        )
    } else{
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