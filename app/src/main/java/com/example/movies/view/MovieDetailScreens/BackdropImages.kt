package com.example.movies.view.MovieDetailScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.movies.Constants
import com.example.movies.model.Backdrop

@Composable
fun BackdropImages(backdrops: List<Backdrop>) {
    if (backdrops != null && backdrops.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(backdrops.size) { index ->
                BackdropItem(backdrops[index])
            }
        }
    }
}

@Composable
fun BackdropItem(backdrop: Backdrop) {
    Card(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Image(
            painter = rememberImagePainter(Constants.IMAGE_BASE_URL + backdrop.file_path),
            contentDescription = null,
            Modifier
                .height(100.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
    }
}