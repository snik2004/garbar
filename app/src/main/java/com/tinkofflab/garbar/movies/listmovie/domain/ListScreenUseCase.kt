package com.tinkofflab.garbar.movies.listmovie.domain

import com.tinkofflab.garbar.db.AppDatabase
import com.tinkofflab.garbar.movies.listmovie.data.dbo.FilmDbo
import com.tinkofflab.garbar.movies.listmovie.data.entity.Film
import com.tinkofflab.garbar.api.FilmsApiRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber

const val TYPE_POPULAR = "TOP_100_POPULAR_FILMS"

class ListScreenUseCase(
    private val apiRepository: FilmsApiRepository,
    private val appDatabase: AppDatabase,
) {

    fun getPopularMovies(): Single<List<Film>> {
        return Single.zip(
            apiRepository.getPopularFilms(type = TYPE_POPULAR),
            getBookmarksFilmIds()
        ) { listFilmsResponse, favouriteFilmIds ->
            val listFilm = mutableListOf<Film>()
            listFilmsResponse.films.map { film ->
                listFilm.add(film.toUi().copy(isFavourite = favouriteFilmIds.contains(film.filmId)))
            }
            listFilm.toList()
        }
    }

    @Synchronized
    fun updateAllFilms(films: List<Film>): Single<List<Film>> {
        return getBookmarksFilmIds().map { favouriteFilmIds ->
            val updateFilms = mutableListOf<Film>()
            films.map { film ->
                updateFilms.add(film.copy(isFavourite = favouriteFilmIds.contains(film.filmId)))
            }
            updateFilms.toList()
        }
    }

    private fun getBookmarksFilmIds(): Single<Set<Int>> {
        return appDatabase.filmsDao().getAll()
            .map { filmDboList ->
                filmDboList.map { it.filmId }.toSet()
            }
    }

    fun getSelectFilm(filmId: Int): Single<Film> {
        return apiRepository.getSelectFilm(filmId = filmId)
            .map { filmsResponse ->
                filmsResponse.toUi()
            }
    }

    @Synchronized
    fun putAllFilmsToDb(films: List<Film>): Completable {
        Timber.d("db cache: put ${films.size} films)")
        val dbos: List<FilmDbo> = films.map { film: Film ->
            FilmDbo.fromUi(film)
        }
        return appDatabase.filmsDao().insertAll(dbos)
    }

    @Synchronized
    fun getAllFilmsFromDb(): Single<List<Film>> {
        return appDatabase.filmsDao().getAll()
            .map { dbos ->
                Timber.d("db cache: get ${dbos.size} films dbos)")
                dbos.map { filmDbo: FilmDbo -> filmDbo.toUi() }
            }
    }

    @Synchronized
    fun setBookmarksFilm(film: Film): Single<Unit> {
        return appDatabase.filmsDao().insertOne(FilmDbo.fromUi(film))
    }

    @Synchronized
    fun deleteBookmarksFilm(film: Film): Single<Unit> {
        return appDatabase.filmsDao().deleteOne(FilmDbo.fromUi(film))
    }


}