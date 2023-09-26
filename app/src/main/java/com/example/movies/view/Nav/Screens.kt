package com.example.movies.view.Nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreens(val route:String, val title:String, val icon:ImageVector) {

    object Home: BottomBarScreens(route = "home", title = "Home", icon = Icons.Default.Home)
    object Favourites: BottomBarScreens(route = "favourites", title = "Favourites", icon = Icons.Default.Favorite)
    object Profile: BottomBarScreens(route = "profile", title = "Profile", icon = Icons.Default.Person)

}

sealed class Screens(val route:String){
    object MovieDetailScreen: Screens(route = "movie_detail")
    object BackdropImages: Screens(route = "backdrops")
    object SignUp: Screens(route = "registration")
    object Login: Screens(route = "login")
    object Trailer: Screens(route = "trailer")

}