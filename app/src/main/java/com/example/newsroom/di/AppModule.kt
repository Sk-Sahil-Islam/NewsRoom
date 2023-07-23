package com.example.newsroom.di

import android.content.Context
import androidx.room.Room
import com.example.newsroom.data.remote.NewsApi
import com.example.newsroom.db.ArticleDao
import com.example.newsroom.db.NewsDatabase
import com.example.newsroom.repository.NewsRepository
import com.example.newsroom.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        api: NewsApi,
        dao: ArticleDao
    ) = NewsRepository(api, dao)

    @Singleton
    @Provides
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news.db"
        ).build()
    }

    @Provides
    fun provideArticleDao(newsDatabase: NewsDatabase): ArticleDao {
        return newsDatabase.dao()
    }
}