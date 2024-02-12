package com.tinkofflab.garbar.movies.listmovie.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinkofflab.garbar.common.DisposableViewModel
import com.tinkofflab.garbar.movies.listmovie.data.entity.Film
import com.tinkofflab.garbar.movies.listmovie.domain.ListScreenState
import com.tinkofflab.garbar.movies.listmovie.domain.ListScreenUseCase
import com.tinkofflab.garbar.movies.listmovie.domain.ViewMode
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class ListMoviesViewModel(
    private val useCase: ListScreenUseCase,
) : DisposableViewModel() {

    private val _listFilmsLD = MutableLiveData<ListScreenState>()
    val listFilmsLD: LiveData<ListScreenState> = _listFilmsLD

    init {
        getMovies()
    }

    fun getMovies() {
        val d = useCase.getPopularMovies().toObservable()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                _listFilmsLD.postValue(
                    ListScreenState(
                        isLoading = true,
                        films = listOf()
                    )
                )
            }
            .subscribe({ films ->
                _listFilmsLD.postValue(ListScreenState(isLoading = false, films = films))
            }) {
                Timber.e("error get movies = ${it.message}")
                _listFilmsLD.postValue(
                    ListScreenState(
                        isLoading = false,
                        films = mutableListOf(),
                        error = it
                    )
                )
            }
        addDisposable(d)
    }

    fun getAllFilmsFromDb(viewMode: ViewMode) {
        val films: MutableList<Film> = listFilmsLD.value?.films?.toMutableList() ?: mutableListOf()
        val d = useCase.getAllFilmsFromDb()
            .subscribeOn(Schedulers.io())
            .subscribe({ bookmarks ->
                _listFilmsLD.postValue(
                    ListScreenState(
                        isLoading = false,
                        films = films,
                        bookmarks = bookmarks,
                        viewMode = viewMode,
                    )
                )
            }) {
                Timber.e("error get films from bd $it")
            }
        addDisposable(d)
    }

    fun setBookmarksFilm(film: Film) {
        val bookmarks: MutableList<Film> =
            listFilmsLD.value?.bookmarks?.toMutableList() ?: mutableListOf()
        val films: MutableList<Film> = listFilmsLD.value?.films?.toMutableList() ?: mutableListOf()
        val viewMode: ViewMode = listFilmsLD.value?.viewMode ?: ViewMode.All
        val d = useCase.getSelectFilm(film.filmId)
            .map { filmWithDescription ->
                val indexBookmarks = getIndexItem(bookmarks, film.filmId)
                if (bookmarks.isNotEmpty() && !containsElement(bookmarks, film.filmId) && indexBookmarks != -1) {
                    bookmarks.set(indexBookmarks, film.copy(isFavourite = true, description = filmWithDescription.description))
                } else {
                    bookmarks.add(film.copy(isFavourite = true, description = filmWithDescription.description))
                }
                films.set(getIndexItem(films, film.filmId), film.copy(isFavourite = true, description = filmWithDescription.description))
                filmWithDescription
            }.flatMap { filmWithDescription ->
                useCase.setBookmarksFilm(filmWithDescription.copy(isFavourite = true))
            }.flatMap {
                useCase.updateAllFilms(films).flatMap { updateFilms ->
                    useCase.getAllFilmsFromDb().map { updateBookmarks ->
                        _listFilmsLD.postValue(
                            ListScreenState(
                                isLoading = false,
                                films = updateFilms,
                                bookmarks = updateBookmarks,
                                viewMode = viewMode
                            )
                        )
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .subscribe({}) {
                Timber.e("error put films to bd $it")
            }
        addDisposable(d)
    }


    private fun containsElement(films: List<Film>, filmId: Int): Boolean {
        return films.filter { it.filmId == filmId }.size == 1
    }

    private fun getIndexItem(films: List<Film>, filmId: Int): Int {
        return films.indexOfFirst { it.filmId == filmId }
    }

    fun deleteBookmarksFilm(film: Film) {
        val bookmarks: MutableList<Film> =
            listFilmsLD.value?.bookmarks?.toMutableList() ?: mutableListOf()
        val films: MutableList<Film> = listFilmsLD.value?.films?.toMutableList() ?: mutableListOf()
        val viewMode: ViewMode = listFilmsLD.value?.viewMode ?: ViewMode.All
        val d = useCase.deleteBookmarksFilm(film).flatMap {
            if (bookmarks.isNotEmpty() && containsElement(bookmarks, film.filmId)) {
                bookmarks.remove(film)
            }
            films.set(getIndexItem(films, film.filmId), film.copy(isFavourite = false))
            useCase.updateAllFilms(films).map { updateFilms ->
                _listFilmsLD.postValue(
                    ListScreenState(
                        isLoading = false,
                        films = updateFilms,
                        bookmarks = bookmarks,
                        viewMode = viewMode
                    )
                )
            }
        }
            .subscribeOn(Schedulers.io())
            .subscribe({}) {
                Timber.e("error delete films from bd $it")
            }
        addDisposable(d)
    }

    fun returnToAllFilm() {
        val films: MutableList<Film> = listFilmsLD.value?.films?.toMutableList() ?: mutableListOf()
        Timber.i("films size = ${films.size}")
        _listFilmsLD.postValue(
            ListScreenState(
                isLoading = false,
                films = films,
                viewMode = ViewMode.All
            )
        )
    }

    fun updateFilmInList(films: MutableList<Film>, updatedFilm: Film): List<Film> {
        for (i in 0 until films.size) {
            if (films[i].filmId == updatedFilm.filmId) {
                films[i] = updatedFilm
                return films
            }
        }
        return films
    }
}