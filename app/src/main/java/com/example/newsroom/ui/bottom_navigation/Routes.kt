package com.example.newsroom.ui.bottom_navigation

sealed class Routes(val route: String){
    object IndividualNews: Routes(route = "news")
}

