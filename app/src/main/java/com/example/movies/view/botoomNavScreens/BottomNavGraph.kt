package com.example.movies.view.botoomNavScreens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies.model.Backdrop
import com.example.movies.model.MovieWithIsScreenFav

@Composable
fun BottomNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.SignUp.route
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
            Profile()
        }

        composable(route = Screens.MovieDetailScreen.route) {
            val movieWithIsScreenFav =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<MovieWithIsScreenFav>("movieDetail")
            movieWithIsScreenFav.let {
                if (movieWithIsScreenFav != null) {
                    MovieDetail(movie = movieWithIsScreenFav.movie,isInFav = movieWithIsScreenFav.isOnFavouriteScreen, navHostController = navHostController)
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
    }

}