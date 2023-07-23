package com.example.newsroom.db

import androidx.room.TypeConverter
import com.example.newsroom.data.remote.responses.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}