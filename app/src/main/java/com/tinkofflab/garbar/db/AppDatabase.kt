package com.tinkofflab.garbar.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tinkofflab.garbar.movies.listmovie.data.dbo.FilmsDao
import com.tinkofflab.garbar.movies.listmovie.data.dbo.FilmDbo

@Database(entities = [FilmDbo::class], version = 2)
@TypeConverters(CountryListConverter::class, GenreListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmsDao(): FilmsDao
}