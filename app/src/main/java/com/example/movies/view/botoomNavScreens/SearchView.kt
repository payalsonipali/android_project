package com.example.movies.view.botoomNavScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchMovieScreen(onBackClick: () -> Unit) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    val textState = remember { mutableStateOf("Search here...") }
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
            // Leftmost icon
            Icon(
                painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    onBackClick()
                }
            )

            // Spacer to separate the icon and title
            Spacer(modifier = Modifier.width(8.dp))

            // Title centered horizontally
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = null,
                    tint = Color.White
                )

                BasicTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .focusRequester(focusRequester),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                    value = textState.value,
                    onValueChange = { newText ->
                        textState.value = newText
                        coroutineScope.launch {
                            movieViewModel.setEndpoint("search/movie", newText)
                        }
                    }
                )

                Icon(
                    painterResource(id = R.drawable.baseline_keyboard_voice_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }

}