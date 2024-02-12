package com.tinkofflab.garbar

import androidx.room.Room
import com.tinkofflab.garbar.api.ApiProvider
import com.tinkofflab.garbar.db.AppDatabase
import com.tinkofflab.garbar.movies.detail.ui.DetailsFilmViewModel
import com.tinkofflab.garbar.movies.detail.ui.DetailsUseCase
import com.tinkofflab.garbar.api.FilmsApiRepository
import com.tinkofflab.garbar.movies.listmovie.domain.ListScreenUseCase
import com.tinkofflab.garbar.movies.listmovie.ui.ListMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { FilmsApiRepository.provideMoviesRepo(get()) }
    single { ApiProvider.provideRetrofit(get()) }
    single { ApiProvider.provideAuthInterceptor() }
    viewModel { ListMoviesViewModel(get()) }
    single { ListScreenUseCase(get(), get()) }
    viewModel {(filmId: Int, isFromBookmarks: Boolean) -> DetailsFilmViewModel(filmId, get(), isFromBookmarks) }
    single { DetailsUseCase(get(), get()) }
    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }
}