package com.example.movies.view.botoomNavScreens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.Constants.items
import com.example.movies.Constants.mapOfMovieCategory
import com.example.movies.R
import com.example.movies.model.Movie
import com.example.movies.ui.theme.green
import com.example.movies.ui.theme.grey
import com.example.movies.ui.theme.light_grey
import com.example.movies.viewmodel.FavouritesViewModel
import com.example.movies.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    val isLoading = movieViewModel.isLoading.value
    var isSearchVisible by remember { mutableStateOf(false) }

    if (movieViewModel.selectedItem == null && items.isNotEmpty()) {
        movieViewModel.selectedItem = items[0]
    }

    Scaffold(
        content = {
            Column {
                Column(
                    modifier = Modifier
                        .background(grey)
                        .padding(10.dp)
                ) {
                    if (isSearchVisible) {
                        SearchMovieScreen(onBackClick = { isSearchVisible = false })
                    } else {
                        RowOfTextAndSearch(onSearchClick = { isSearchVisible = true })
                        HorizontalScrollableRowWithSelection(
                            items = items,
                            selectedItem = movieViewModel.selectedItem,
                            onItemSelected = { item ->
                                movieViewModel.selectedItem =
                                    if (item == movieViewModel.selectedItem) null else item
                            }
                        )
                    }
                }
                movieViewModel.selectedItem?.let { selected ->
                    if (!isSearchVisible) {
                        mapOfMovieCategory.get(selected)
                            ?.let { movieViewModel.setEndpoint(it, null) }
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        val movies = movieViewModel.moviePagerFlow.collectAsLazyPagingItems()

                        RecyclerView(selected, navHostController, movies, false)
                        CircularProgressBar(isLoading)
                    }
                }
            }
        }
    )
}

@Composable
fun RowOfTextAndSearch(onSearchClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Watch Now",
            color = Color.White,
            fontSize = 35.sp,
        )

        Icon(
            painterResource(id = R.drawable.baseline_search_24),
            contentDescription = "search",
            tint = Color.White,
            modifier = Modifier.clickable {
                onSearchClick()
            }
        )
    }
}

@Composable
fun HorizontalScrollableRowWithSelection(
    items: List<String>,
    selectedItem: String?,
    onItemSelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items.forEach { item ->
            item {
                val isSelected = item == selectedItem
                HorizontalScrollableRowItem(
                    text = item,
                    isSelected = isSelected,
                    onItemClick = { onItemSelected(item) }
                )
            }
        }
    }
}

@Composable
fun HorizontalScrollableRowItem(
    text: String,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(50.dp)
            .padding(5.dp)
            .clip(RoundedCornerShape(25.dp))
            .toggleable(
                value = isSelected,
                onValueChange = { onItemClick() }
            ),
        colors = CardDefaults.cardColors(if (isSelected) green else light_grey),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                color = Color.White
            )
        }

    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RecyclerView(
    selected: String,
    navHostController: NavHostController,
    movies: LazyPagingItems<Movie>,
    isOnFavoriteScreen: Boolean,
) {
    val favoriteIdViewModel: FavouritesViewModel = hiltViewModel()

    val lazyListState = rememberLazyGridState()

    // Scroll to item 0 when selected category changes
    LaunchedEffect(selected) {
        lazyListState.scrollToItem(0)
    }
    val favoriteIds by favoriteIdViewModel.favoriteIdsFlow.collectAsState(emptyList())

    LaunchedEffect(key1 = favoriteIds) {
        Log.d("taggg","favids refreshed : $favoriteIds")
            movies.refresh()
    }

    LazyVerticalGrid(
        state = lazyListState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        items(movies.itemCount) { index ->
            movies[index]?.let {
                val isFav = favoriteIds?.contains(movies[index]?.id ?: 0) == true
                Log.d("taggg","isfav isfav : $isFav")
                ListItem(it, navHostController, isOnFavoriteScreen)
            }
        }
    }
}

