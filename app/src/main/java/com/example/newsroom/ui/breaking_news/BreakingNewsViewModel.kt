package com.example.newsroom.ui.breaking_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.newsroom.paging.BreakingNewsPagingSource
import com.example.newsroom.repository.NewsRepository
import com.example.newsroom.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    val pager = Pager(
        PagingConfig(pageSize = Constants.PAGE_SIZE)
    ) {
        BreakingNewsPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

}
