package com.example.newsroom.ui.search_news

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsroom.data.remote.responses.Article
import com.example.newsroom.paging.SearchNewsPagingSource
import com.example.newsroom.repository.NewsRepository
import com.example.newsroom.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _items = mutableStateListOf<String>()
    val items = _items

    suspend fun updateItems(item: String) {
        delay(300L)
        if (item.isNotEmpty()){
            _items.add(item)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchHeroes(query: String): Flow<PagingData<Article>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGE_SIZE)
        ) {
            SearchNewsPagingSource(repository = repository, searchQuery = query)
        }.flow.cachedIn(viewModelScope)
    }
}