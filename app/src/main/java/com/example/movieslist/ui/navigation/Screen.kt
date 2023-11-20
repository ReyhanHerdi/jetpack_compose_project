package com.example.movieslist.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailMovie : Screen("home/{id}") {
        fun createRoute(id: String) = "home/$id"
    }
}
