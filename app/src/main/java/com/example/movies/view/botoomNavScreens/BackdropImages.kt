package com.example.movies.view.botoomNavScreens

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
import com.example.movies.model.Backdrops

@Composable
fun BackdropImages(backdrops: Backdrops?) {
    if (backdrops != null && backdrops.backdrops.isNotEmpty()) {
        val backdrops = backdrops.backdrops
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(backdrops.size) { index ->
                BackdropItem(backdrop = backdrops.get(index))
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