package com.example.newsroom.ui.breaking_news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsroom.data.remote.responses.Article
import com.example.newsroom.ui.bottom_navigation.Routes
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BreakingNewsScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        BreakingNewsList(navController = navController)
    }
}

@Composable
fun BreakingNewsList(
    viewModel: BreakingNewsViewModel = hiltViewModel(),
    navController: NavController
) {
    val lazyPagingItems = viewModel.pager.collectAsLazyPagingItems()

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = "News are loading please wait...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
        items(count = lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let {
                NewsEntry(
                    entry = it,
                    modifier = Modifier.clickable {
                        val encodedUrl = URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())
                        navController.navigate(Routes.IndividualNews.route + "/${encodedUrl}")
                    }
                )
            }
        }
        if (lazyPagingItems.loadState.append == LoadState.NotLoading(endOfPaginationReached = true)) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(bottom = 16.dp),
                    text = "End Reached",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        when (lazyPagingItems.loadState.append) {
            is LoadState.Error -> Unit
            LoadState.Loading -> {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
            else -> {}
        }
    }
}

@Composable
fun NewsEntry(
    modifier: Modifier = Modifier,
    entry: Article
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Min)
        ) {
            Column(modifier = Modifier.width(IntrinsicSize.Min), verticalArrangement = Arrangement.Center) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            entry.urlToImage
                                ?: "https://t4.ftcdn.net/jpg/04/73/25/49/360_F_473254957_bxG9yf4ly7OBO5I0O5KABlN930GwaMQz.jpg"
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = entry.title,
                    modifier = Modifier
                        .size(width = 130.dp, height = 85.dp)
                        .align(CenterHorizontally)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = entry.source.name,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )
                Text(
                    text = entry.publishedAt,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.offset(y = (-4).dp),
                    text = entry.title,
                    fontWeight = FontWeight.Medium,
                    maxLines = 3,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = entry.description ?: "No description available",
                    maxLines = 5,
                    fontSize = 12.sp
                )
            }
        }
    }
}

