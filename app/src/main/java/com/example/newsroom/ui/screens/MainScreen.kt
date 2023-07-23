package com.example.newsroom.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsroom.ui.bottom_navigation.BottomBarScreen
import com.example.newsroom.ui.bottom_navigation.NavigationGraph
import com.example.newsroom.R
import com.example.newsroom.ui.bottom_navigation.Routes

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var showBottomBar by rememberSaveable{ mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        Routes.IndividualNews.route + "/{url}" -> false
        else  -> true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController = navController)
            }
        },
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.BreakingNews,
        BottomBarScreen.SearchNews,
        BottomBarScreen.SavedNews
    )
    val icon1 = painterResource(id = R.drawable.baseline_newspaper_24)
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {

        BottomNavigationItem(
            selected = screens[0].route == navBackStackEntry?.destination?.route,
            onClick = {
                navController.navigate(screens[0].route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(painter = icon1, contentDescription = null)
            },
            label = {
                Text(text = screens[0].title)
            },
            unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
        )
        screens.subList(1, screens.size).forEach { screen ->
            BottomNavigationItem(
                selected = screen.route == navBackStackEntry?.destination?.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = null)
                },
                label = {
                    Text(text = screen.title)
                },
                unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
            )
        }
    }
}