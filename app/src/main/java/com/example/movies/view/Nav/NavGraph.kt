package com.example.movies.view.Nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies.model.Backdrop
import com.example.movies.model.MovieWithIsScreenFav
import com.example.movies.view.AuthScreens.LoginScreen
import com.example.movies.view.AuthScreens.SignUpScreen
import com.example.movies.view.MovieDetailScreens.BackdropImages
import com.example.movies.view.MovieDetailScreens.MovieDetail
import com.example.movies.view.MovieDetailScreens.TrailerScreen
import com.example.movies.view.botoomNavScreens.Favourites
import com.example.movies.view.botoomNavScreens.HomeScreen
import com.example.movies.view.botoomNavScreens.Profile
import com.example.movies.viewmodel.SigninViewModel

@Composable
fun BottomNavGraph(navHostController: NavHostController) {
    val authViewModel:SigninViewModel = hiltViewModel()

    NavHost(
        navController = navHostController,
        startDestination = if(authViewModel.isUserLoggedIn()) BottomBarScreens.Home.route else Screens.Login.route
    ) {
        composable(route = Screens.SignUp.route) {
            SignUpScreen(navHostController)
        }

        composable(route = Screens.Login.route) {
            LoginScreen(navHostController)
        }

        composable(route = BottomBarScreens.Home.route) {
            HomeScreen(navHostController)
        }
        composable(route = BottomBarScreens.Favourites.route) {
            Favourites(navHostController)
        }
        composable(route = BottomBarScreens.Profile.route) {
            Profile(navHostController)
        }

        composable(route = Screens.MovieDetailScreen.route) {
            val movieWithIsScreenFav =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<MovieWithIsScreenFav>(
                    "movieDetail"
                )
            movieWithIsScreenFav.let {
                if (movieWithIsScreenFav != null) {
                    MovieDetail(
                        movie = movieWithIsScreenFav.movie,
                        isInFav = movieWithIsScreenFav.isOnFavouriteScreen,
                        navHostController = navHostController
                    )
                }
            }
        }

        composable(route = Screens.BackdropImages.route) {
            val backdrops =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<List<Backdrop>>("backdrops")
            if (backdrops != null) {
                BackdropImages(backdrops)
            }
        }

        composable(route = Screens.Trailer.route) {
            val trailer =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<String>("trailer")
            if (trailer != null) {
                TrailerScreen(youtubeVideoId = trailer)
            }
        }

    }

}