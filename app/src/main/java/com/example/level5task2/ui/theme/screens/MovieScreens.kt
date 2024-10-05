package com.example.level5task2.ui.theme.screens

sealed class MovieScreens (
    val route: String,

    ){
    object OverviewScreen : MovieScreens("Overview")
    object FavoritesScreen : MovieScreens("Favorites")
    object MovieDetailsScreen : MovieScreens("MovieDetails")

}