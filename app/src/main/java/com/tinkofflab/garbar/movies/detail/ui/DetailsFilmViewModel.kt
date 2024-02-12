package com.tinkofflab.garbar.movies.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinkofflab.garbar.common.DisposableViewModel
import com.tinkofflab.garbar.movies.detail.domain.DetailsScreenState
import com.tinkofflab.garbar.movies.listmovie.domain.ListScreenState
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class DetailsFilmViewModel(filmId: Int, useCase: DetailsUseCase, isFromBookmarks: Boolean) :
    DisposableViewModel() {

    private val _stateFilmsLD = MutableLiveData<DetailsScreenState>()
    val stateFilmsLD: LiveData<DetailsScreenState> = _stateFilmsLD

    init {
        val d = if (!isFromBookmarks) {
            useCase.getSelectFilm(filmId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    _stateFilmsLD.postValue(
                        DetailsScreenState(
                            isLoading = true,
                            film = null
                        )
                    )
                }
                .subscribe({ film ->
                    _stateFilmsLD.postValue(DetailsScreenState(isLoading = false, film = film))
                }) {
                    Timber.e("error get movies = ${it.message}")
                    _stateFilmsLD.postValue(
                        DetailsScreenState(
                            isLoading = false,
                            film = null,
                            error = it
                        )
                    )
                }
        } else {
            useCase.getSelectFilmFromDb(filmId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    _stateFilmsLD.postValue(
                        DetailsScreenState(
                            isLoading = true,
                            film = null
                        )
                    )
                }
                .subscribe({ film ->
                    _stateFilmsLD.postValue(DetailsScreenState(isLoading = false, film = film))
                }) {
                    Timber.e("error get movies = ${it.message}")
                    _stateFilmsLD.postValue(
                        DetailsScreenState(
                            isLoading = false,
                            film = null,
                            error = it
                        )
                    )
                }
        }
        addDisposable(d)
    }
}