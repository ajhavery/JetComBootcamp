package com.ajhavery.jetmovieapp.navigation

import java.lang.IllegalArgumentException

// using enums - we list all possible screens
enum class MovieScreens {
    HomeScreen,
    DetailsScreen;
    companion object {
        fun fromRoute(route: String?): MovieScreens =
            when (route?.substringBefore("/")) { // what is route after /
                HomeScreen.name -> HomeScreen
                DetailsScreen.name -> DetailsScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }
    }
}