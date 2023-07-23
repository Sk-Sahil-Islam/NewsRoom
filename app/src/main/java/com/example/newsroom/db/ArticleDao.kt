package com.example.newsroom.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsroom.data.remote.responses.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Upsert
    suspend fun upsertArticle(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}