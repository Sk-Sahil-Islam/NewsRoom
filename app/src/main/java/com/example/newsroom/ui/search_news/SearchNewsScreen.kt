package com.example.newsroom.ui.search_news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsroom.ui.breaking_news.NewsEntry

import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import androidx.paging.LoadState
import com.example.newsroom.R
import com.example.newsroom.ui.bottom_navigation.Routes
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SearchNewsScreen(
    viewModel: SearchNewsViewModel = hiltViewModel(),
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SearchTopBar()
            if (viewModel.searchQuery.value.isNotEmpty()) {
                SearchNewsList(searchQuery = viewModel.searchQuery.value, navController = navController)
            }
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    DelicateCoroutinesApi::class
)
@Composable
fun SearchTopBar(
    viewModel: SearchNewsViewModel = hiltViewModel()
) {
    var text by remember { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val history = remember { viewModel.items }
    Column(modifier = Modifier.fillMaxWidth()) {
        SearchBar(
            modifier = Modifier.align(CenterHorizontally),
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                active = false
                viewModel.updateSearchQuery(it)
                GlobalScope.launch {
                    viewModel.updateItems(text)
                    text = ""
                }
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = { Text(text = "Search news...") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (text.isNotEmpty()) {
                                text = ""
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colors.background,
                inputFieldColors = TextFieldDefaults.colors(
                    unfocusedTextColor = MaterialTheme.colors.onBackground,
                    focusedTextColor = MaterialTheme.colors.onBackground
                )
            )
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(history.size) {
                    Row(
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            painter = painterResource(id = R.drawable.baseline_history_24),
                            contentDescription = "history"
                        )
                        Text(text = history[history.size - (it + 1)])
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun SearchNewsList(
    viewModel: SearchNewsViewModel = hiltViewModel(),
    searchQuery: String,
    navController: NavController
) {
    val lazyPagingItems = viewModel.searchHeroes(searchQuery).collectAsLazyPagingItems()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = "News are loading...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
        items(count = lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let { NewsEntry(
                entry = it,
                modifier = Modifier.clickable {
                    val encodedUrl = URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())
                    navController.navigate(Routes.IndividualNews.route + "/${encodedUrl}")
                }
            ) }
        }
        if (lazyPagingItems.loadState.append == LoadState.NotLoading(endOfPaginationReached = true)) {
            item {
                Text(modifier = Modifier
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
                            .wrapContentWidth(CenterHorizontally)
                    )
                }
            }
            else -> {}
        }
    }
}