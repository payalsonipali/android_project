package com.example.movies.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movies.ui.theme.grey
import com.example.movies.ui.theme.light_grey
import com.example.movies.view.Nav.BottomBarScreens
import com.example.movies.view.Nav.BottomNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navHostController = navController) }
    ) {
        BottomNavGraph(navHostController = navController)
    }
}

@Composable
fun BottomBar(navHostController: NavHostController) {
    val screens = listOf(
        BottomBarScreens.Home,
        BottomBarScreens.Favourites,
        BottomBarScreens.Profile
    )

    //whenever value change it will notify
    val navBackStack by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStack?.destination

    val isBottomBarVisible = currentDestination?.route in screens.map { it.route }

    if(isBottomBarVisible) {
        BottomNavigation(
            backgroundColor = grey
        ) {
            screens.forEach { screen ->
                AddItems(
                    screen = screen,
                    currenDestination = currentDestination,
                    navHostController = navHostController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItems(
    screen: BottomBarScreens,
    currenDestination: NavDestination?,
    navHostController: NavHostController
) {
    val isSelected = currenDestination?.hierarchy?.any{it.route == screen.route} == true
    BottomNavigationItem(
        label = {
            Text(text = screen.title, color = if(isSelected)Color.White else light_grey)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "naviation icon"
            )
        },
        selected = isSelected,
        unselectedContentColor = light_grey,
        selectedContentColor = Color.White,
        onClick ={
            navHostController.navigate(screen.route){
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}