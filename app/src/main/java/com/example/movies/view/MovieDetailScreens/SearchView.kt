package com.example.movies.view.MovieDetailScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.ui.theme.light_grey
import com.example.movies.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchMovieScreen(onBackClick: () -> Unit) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    val textState = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    onBackClick()
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .weight(1f) // Take up remaining horizontal space
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "Finder",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF444446))
                .graphicsLayer(alpha = 0.7f),
            shadowElevation = 8.dp,
            color = Color(0xFF444446)
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                value = textState.value,
                onValueChange = { newText ->
                    textState.value = newText
                },
                placeholder = {
                    Text(
                        text = "Search here...",
                        style = TextStyle(color = Color.White)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Perform search or other action here
                        if (textState.value.length > 3) {
                            coroutineScope.launch {
                                movieViewModel.setEndpoint(
                                    "search/movie",
                                    textState.value
                                )
                            }
                        }
                    }
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null, // Set to null if not needed
                        tint = light_grey
                    )
                },
                trailingIcon = {
                    if (textState.value.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null, // Set to null if not needed
                            tint = light_grey,
                            modifier = Modifier.clickable {
                                textState.value = ""
                                coroutineScope.launch {
                                    movieViewModel.setEndpoint(
                                        "search/movie",
                                        "a"
                                    )
                                }
                            }
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = light_grey,
                    textColor = light_grey,
                    disabledTextColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent, // Remove the focused underline
                    unfocusedIndicatorColor = Color.Transparent // Remove the unfocused underline
                )
            )

        }
    }

}