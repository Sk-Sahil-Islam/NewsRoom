package com.example.newsroom.ui.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object BreakingNews : BottomBarScreen(
        route = "breaking_news",
        title = "Breaking News",
        icon = Icons.Default.Home
    )

    object SearchNews : BottomBarScreen(
        route = "search_news",
        title = "Search News",
        icon = Icons.Default.Search
    )

    object SavedNews : BottomBarScreen(
        route = "saved_news",
        title = "Saved",
        icon = Icons.Default.Favorite
    )
}
