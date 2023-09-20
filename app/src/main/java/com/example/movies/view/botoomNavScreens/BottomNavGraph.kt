package com.example.movies.view.botoomNavScreens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies.model.Backdrops
import com.example.movies.model.MovieAllDetail

@Composable
fun BottomNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BottomBarScreens.Home.route
    ) {
        composable(route = BottomBarScreens.Home.route) {
            HomeScreen(navHostController)
        }
        composable(route = BottomBarScreens.Favourites.route) {
            Favourites()
        }
        composable(route = BottomBarScreens.Profile.route) {
            Profile()
        }

        composable(route = Screens.MovieDetailScreen.route) {
            val movieAllDetail =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<MovieAllDetail>("movieDetail")
            movieAllDetail.let {
                if (movieAllDetail != null) {
                    MovieDetail(movieAllDetail = movieAllDetail, navHostController = navHostController)
                }
            }
        }

        composable(route = Screens.BackdropImages.route) {
            val backdrops =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<Backdrops>("backdrops")
            BackdropImages(backdrops)
        }
    }

}