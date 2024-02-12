package com.tinkofflab.garbar.api

import com.tinkofflab.garbar.movies.listmovie.data.dto.SingleFilmDto
import com.tinkofflab.garbar.movies.listmovie.data.dto.FilmsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsApiRepository {

    @GET("films/top")
    fun getPopularFilms(@Query("type") type: String): Single<FilmsResponse>

    @GET("films/{id}")
    fun getSelectFilm(@Path("id") filmId: Int): Single<SingleFilmDto>

    companion object {
        fun provideMoviesRepo(retrofit: Retrofit): FilmsApiRepository {
            return retrofit.create(FilmsApiRepository::class.java)
        }
    }
}