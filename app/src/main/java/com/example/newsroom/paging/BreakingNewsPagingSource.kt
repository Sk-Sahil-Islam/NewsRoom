package com.example.newsroom.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsroom.data.remote.responses.Article
import com.example.newsroom.repository.NewsRepository

class BreakingNewsPagingSource(
    private val repository: NewsRepository
): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val currentPage = params.key ?: 1
        return try {
            val response = repository.getBreakingNews(pageNumber = currentPage)
            val endOfPaginationReached = response.data!!.articles.isEmpty()
            if (response.data.articles.isNotEmpty()){
                LoadResult.Page(
                    data = response.data.articles,
                    prevKey = null,
                    nextKey = if(endOfPaginationReached) null else currentPage + 1
                )
            }else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

        } catch (e:Exception) {
            LoadResult.Error(e)
        }

    }
}