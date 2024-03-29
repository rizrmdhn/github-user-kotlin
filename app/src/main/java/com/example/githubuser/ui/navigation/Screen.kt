package com.example.githubuser.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object DetailUser : Screen("home/{username}") {
        fun createRoute(username: String) = "home/$username"
    }
}