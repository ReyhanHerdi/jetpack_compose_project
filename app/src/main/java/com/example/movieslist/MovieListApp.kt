package com.example.movieslist

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieslist.ui.navigation.NavigationItem
import com.example.movieslist.ui.navigation.Screen
import com.example.movieslist.ui.screen.bookmark.BookmarkScreen
import com.example.movieslist.ui.screen.detail.DetailScreen
import com.example.movieslist.ui.screen.home.HomeScreen
import com.example.movieslist.ui.screen.profile.ProfileScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieListApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    application: Application
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRute != Screen.DetailMovie.route) {
                BottomBar(navController)
            }
        },
        modifier = Modifier
    ) { _ ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    modifier = Modifier,
                    navigateToDetail = { id ->
                        navController.navigate(Screen.DetailMovie.createRoute(id))
                    },
                    application = application
                )
            }
            composable(Screen.Bookmark.route) {
                BookmarkScreen(
                    modifier = Modifier,
                    navigateToDetail = { id ->
                        navController.navigate(Screen.DetailMovie.createRoute(id))
                    },
                    application = application
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(modifier = modifier)
            }
            composable(
                route = Screen.DetailMovie.route,
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                })
            ) {
                val id = it.arguments?.getString("id") ?: -1L
                Log.d("IDJet", id.toString())
                DetailScreen(
                    id = id.toString(),
                    application = application
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navigationItem = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = "Bookmark",
                icon = Icons.Default.Favorite,
                screen = Screen.Bookmark
            ),
            NavigationItem(
                title = "Profile",
                icon = Icons.Default.Person,
                screen = Screen.Profile
            )
        )
        navigationItem.map { items ->
            NavigationBarItem(
                icon = {
                       Icon(
                           imageVector = items.icon,
                           contentDescription = items.title)
                },
                label = { Text(items.title) },
                selected = false,
                onClick = {
                    navController.navigate(items.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}