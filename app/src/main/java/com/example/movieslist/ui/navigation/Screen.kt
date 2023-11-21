package com.example.movieslist.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Bookmark : Screen("screen")
    object Profile : Screen("profile")
    object DetailMovie : Screen("home/{id}") {
        fun createRoute(id: String) = "home/$id"
    }
}
