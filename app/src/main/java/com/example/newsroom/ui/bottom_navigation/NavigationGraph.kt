package com.example.newsroom.ui.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsroom.ui.breaking_news.BreakingNewsScreen
import com.example.newsroom.ui.news_screen.NewsScreen
import com.example.newsroom.ui.screens.SavedNewsScreen
import com.example.newsroom.ui.search_news.SearchNewsScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = BottomBarScreen.BreakingNews.route ) {
        composable(route = BottomBarScreen.BreakingNews.route) {
            BreakingNewsScreen(navController)
        }
        composable(route = BottomBarScreen.SearchNews.route) {
            SearchNewsScreen(navController = navController)
        }
        composable(route = BottomBarScreen.SavedNews.route) {
            SavedNewsScreen()
        }
        composable(
            route = Routes.IndividualNews.route + "/{url}",
            arguments = listOf(
                navArgument("url"){
                    type = NavType.StringType
                }
            )
        ) {
            val url = remember { it.arguments?.getString("url") }
            if (url != null) {
                NewsScreen(navController = navController , url = url)
            }
        }
    }
}