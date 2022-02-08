package com.ajhavery.jetmovieapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ajhavery.jetmovieapp.screens.details.DetailsScreen
import com.ajhavery.jetmovieapp.screens.home.HomeScreen

@ExperimentalAnimationApi
@Composable
fun MovieNavigation() {
    // Create the navController - the Central API
    val navController = rememberNavController()
    // Create the NavHost and build NavGraph inside it
    NavHost(
        navController = navController,
        startDestination = MovieScreens.HomeScreen.name
    ) {
        composable(MovieScreens.HomeScreen.name) {
            // here we pass the actual composable this route should lead us to
            HomeScreen(navController = navController)
        }
        // /details-screen/id=34
        composable(
            MovieScreens.DetailsScreen.name + "/{movie}",
            arguments = listOf(navArgument(name = "movie") { type = NavType.StringType })
        ) {
            // backStackEntry is the variable which contains information we pass using arguments
                backStackEntry ->
            DetailsScreen(
                navController = navController,
                backStackEntry.arguments?.getString("movie")
            )
        }
    }
}