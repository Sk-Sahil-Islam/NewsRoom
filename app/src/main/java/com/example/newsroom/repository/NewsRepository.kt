package com.example.newsroom.repository

import com.example.newsroom.data.remote.NewsApi
import com.example.newsroom.data.remote.responses.Article
import com.example.newsroom.data.remote.responses.NewsResponse
import com.example.newsroom.db.ArticleDao
import com.example.newsroom.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.delay
import javax.inject.Inject

@ActivityScoped
class NewsRepository @Inject constructor(
    private val api: NewsApi,
    private val dao: ArticleDao
) {
    suspend fun getBreakingNews(pageNumber: Int): Resource<NewsResponse> {
        val response = try {
            delay(2000)
            api.getBreakingNews(pageNumber = pageNumber)

        } catch (e: Exception) {
            return Resource.Error(message = e.toString())
        }
        return Resource.Success(data = response)
    }

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Resource<NewsResponse> {
        val response = try {
            delay(2000)
            api.searchNews(searchQuery = searchQuery, pageNumber = pageNumber)
        } catch (e: Exception) {
            return Resource.Error(message = e.toString())
        }
        return Resource.Success(response)
    }

    fun getAllArticles() = dao.getAllArticles()

    suspend fun upsertArticle(article: Article) {
        dao.upsertArticle(article = article)
    }

    suspend fun deleteArticle(article: Article) {
        dao.deleteArticle(article = article)
    }
}