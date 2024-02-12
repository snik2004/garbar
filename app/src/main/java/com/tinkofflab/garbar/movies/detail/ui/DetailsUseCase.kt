package com.tinkofflab.garbar.movies.detail.ui

import com.tinkofflab.garbar.movies.listmovie.data.entity.Film
import com.tinkofflab.garbar.api.FilmsApiRepository
import com.tinkofflab.garbar.db.AppDatabase
import io.reactivex.rxjava3.core.Single

class DetailsUseCase(private val apiRepository: FilmsApiRepository, private val appDatabase: AppDatabase) {

    fun getSelectFilm(filmId: Int): Single<Film> {
        return apiRepository.getSelectFilm(filmId = filmId)
            .map { filmsResponse ->
                filmsResponse.toUi()
            }
    }

    fun getSelectFilmFromDb(filmId: Int): Single<Film> {
        return appDatabase.filmsDao().getOne(filmId = filmId)
            .map { filmDbo ->
                filmDbo.toUi()
            }
    }


}