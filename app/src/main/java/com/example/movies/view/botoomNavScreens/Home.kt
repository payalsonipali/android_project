package com.example.movies.view.botoomNavScreens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.movies.view.Common.CircularProgressBar
import com.example.movies.view.MovieDetailScreens.ListItem
import com.example.movies.view.MovieDetailScreens.SearchMovieScreen
import com.example.movies.viewmodel.FavouritesViewModel
import com.example.movies.viewmodel.MovieViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    val isLoading = movieViewModel.isLoading.value
    var isSearchVisible by remember { mutableStateOf(false) }
    val errorMessage by movieViewModel.errorLiveData.observeAsState()
    val context = LocalContext.current

    errorMessage?.let { message ->
        if(!message.isEmpty() && !message.isBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
        movieViewModel.clearError()
    }

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

    //handle back press
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    DisposableEffect(backDispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isSearchVisible) {
                    isSearchVisible = !isSearchVisible
                } else {
                    val activity = context as? AppCompatActivity
                    activity?.finish()                }
            }
        }
        backDispatcher?.addCallback(callback)
        onDispose {
            callback.remove()
        }
    }
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

    LazyVerticalGrid(
        state = lazyListState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        items(movies.itemCount) { index ->
            movies[index]?.let {
                val isFav = favoriteIds?.contains(movies[index]?.id ?: 0) == true
                ListItem(it, navHostController, isOnFavoriteScreen)
            }
        }
    }
}

