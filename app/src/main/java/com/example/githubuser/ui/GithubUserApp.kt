package com.example.githubuser.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubuser.ui.navigation.Screen
import com.example.githubuser.ui.screen.detail.DetailScreen
import com.example.githubuser.ui.screen.favorite.FavoriteScreen
import com.example.githubuser.ui.screen.home.HomeScreen
import com.example.githubuser.ui.theme.GithubUserTheme


@Composable
fun GithubUserApp(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(
                route = Screen.Home.route,
            ) {
                HomeScreen(
                    navigateToDetail = { username ->
                        navController.navigate(
                            Screen.DetailUser.createRoute(username)
                        )
                    },
                    navigateToFavorite = {
                        navController.navigate(
                            Screen.Favorite.route
                        )
                    }
                )
            }
            composable(
                route = Screen.Favorite.route,
            ) {
                FavoriteScreen(
                    navigateToDetail = { username ->
                        navController.navigate(
                            Screen.DetailUser.createRoute(username)
                        )
                    },
                    navigateToFavorite = {
                        navController.navigate(
                            Screen.Favorite.route
                        )
                    }
                )
            }
            composable(
                route = Screen.DetailUser.route,
                arguments = listOf(navArgument("username") { type = NavType.StringType }),
            ) {
                val username = it.arguments?.getString("username")
                if (username != null) {
                    DetailScreen(
                        username = username,
                        navigateBack = {
                            navController.popBackStack()
                        },
                        navigateToDetail = { user ->
                            navController.navigate(
                                Screen.DetailUser.createRoute(user)
                            )
                        }
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GithubUserAppPreview() {
    GithubUserTheme {
        GithubUserApp()
    }
}
