package com.example.newsroom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsroom.data.remote.responses.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun dao(): ArticleDao
}