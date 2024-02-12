package com.tinkofflab.garbar.db

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.tinkofflab.garbar.movies.listmovie.data.dbo.GenreDbo

object GenreListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromGenre(genre: String): List<GenreDbo> {
        val listType = object : TypeToken<List<GenreDbo>>() {}.type
        return gson.fromJson(genre, listType)
    }

    @TypeConverter
    fun toGenre(countries: List<GenreDbo>): String {
        return gson.toJson(countries)
    }
}