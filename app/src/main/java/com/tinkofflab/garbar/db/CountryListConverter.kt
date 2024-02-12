package com.tinkofflab.garbar.db

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.tinkofflab.garbar.movies.listmovie.data.dbo.CountryDbo
import com.google.gson.Gson

object CountryListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromCountry(country: String): List<CountryDbo> {
        val listType = object : TypeToken<List<CountryDbo>>() {}.type
        return gson.fromJson(country, listType)
    }

    @TypeConverter
    fun toCountry(countries: List<CountryDbo>): String {
        return gson.toJson(countries)
    }
}