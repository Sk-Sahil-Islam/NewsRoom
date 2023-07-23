package com.example.newsroom.data.remote.responses

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)