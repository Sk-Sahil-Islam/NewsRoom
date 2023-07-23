package com.example.newsroom.data.remote

import com.example.newsroom.data.remote.responses.NewsResponse
import com.example.newsroom.util.Constants.Companion.API_KEY
import com.example.newsroom.util.Constants.Companion.PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int,
        @Query("apiKey")
        apiKey: String = API_KEY,
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int,
        @Query("pageSize")
        pageSize: Int = PAGE_SIZE,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse
}