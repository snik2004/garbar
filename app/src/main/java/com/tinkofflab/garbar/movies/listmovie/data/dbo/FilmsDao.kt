package com.tinkofflab.garbar.movies.listmovie.data.dbo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface FilmsDao {
    @Query("SELECT * FROM films")
    fun getAll(): Single<List<FilmDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(films: List<FilmDbo>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(film: FilmDbo): Single<Unit>

    @Delete
    fun deleteOne(film: FilmDbo): Single<Unit>

    @Update
    fun updateFilm(film: FilmDbo): Completable

    @Query("DELETE from films")
    fun deleteAllFilms(): Completable

    @Query("SELECT * FROM films WHERE filmId = :filmId")
    fun getOne(filmId: Int): Single<FilmDbo>
}