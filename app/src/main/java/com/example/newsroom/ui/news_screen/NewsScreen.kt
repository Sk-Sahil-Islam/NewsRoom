package com.example.newsroom.ui.news_screen

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.newsroom.data.remote.responses.Article
import com.google.accompanist.web.*

@Composable
fun NewsScreen(
    url: String ,
    navController: NavController
) {
    val state = rememberWebViewState(url = url)
    val navigator = rememberWebViewNavigator()
    Column {
        TopAppBar {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back button")
                }
                IconButton(
                    onClick = { navigator.reload() },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "refresh button")
                }
            }
        }
        val loadingState = state.loadingState
        if (loadingState is LoadingState.Loading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = loadingState.progress
            )
        }

        val webClient = remember {
            object : AccompanistWebViewClient() {
                override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Log.d("Accompanist WebView", "Page started loading for $url")
                }
            }
        }

        WebView(
            state = state,
            navigator = navigator,
            onCreated = {
                it.settings.javaScriptEnabled = true
            },
            client = webClient
        )
    }
}