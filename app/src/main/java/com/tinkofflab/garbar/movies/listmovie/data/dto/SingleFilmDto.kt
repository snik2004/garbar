package com.tinkofflab.garbar.movies.listmovie.data.dto

import com.tinkofflab.garbar.movies.listmovie.data.entity.Film
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleFilmDto(
    @SerialName("kinopoiskId") val kinopoiskId: Int,
    @SerialName("kinopoiskHDId") val kinopoiskHDId: String?,
    @SerialName("imdbId") val imdbId: String?,
    @SerialName("nameRu") val nameRu: String,
    @SerialName("nameEn") val nameEn: String?,
    @SerialName("nameOriginal") val nameOriginal: String?,
    @SerialName("posterUrl") val posterUrl: String,
    @SerialName("posterUrlPreview") val posterUrlPreview: String,
    @SerialName("coverUrl") val coverUrl: String?,
    @SerialName("logoUrl") val logoUrl: String?,
    @SerialName("reviewsCount") val reviewsCount: Int,
    @SerialName("ratingGoodReview") val ratingGoodReview: Double?,
    @SerialName("ratingGoodReviewVoteCount") val ratingGoodReviewVoteCount: Int,
    @SerialName("ratingKinopoisk") val ratingKinopoisk: Double?,
    @SerialName("ratingKinopoiskVoteCount") val ratingKinopoiskVoteCount: Int,
    @SerialName("ratingImdb") val ratingImdb: Double?,
    @SerialName("ratingImdbVoteCount") val ratingImdbVoteCount: Int,
    @SerialName("ratingFilmCritics") val ratingFilmCritics: Double?,
    @SerialName("ratingFilmCriticsVoteCount") val ratingFilmCriticsVoteCount: Int,
    @SerialName("ratingAwait") val ratingAwait: Double,
    @SerialName("ratingAwaitCount") val ratingAwaitCount: Int,
    @SerialName("ratingRfCritics") val ratingRfCritics: Double?,
    @SerialName("ratingRfCriticsVoteCount") val ratingRfCriticsVoteCount: Int,
    @SerialName("webUrl") val webUrl: String,
    @SerialName("year") val year: Int,
    @SerialName("filmLength") val filmLength: Int?,
    @SerialName("slogan") val slogan: String?,
    @SerialName("description") val description: String,
    @SerialName("shortDescription") val shortDescription: String?,
    @SerialName("editorAnnotation") val editorAnnotation: String?,
    @SerialName("isTicketsAvailable") val isTicketsAvailable: Boolean,
    @SerialName("productionStatus") val productionStatus: String?,
    @SerialName("type") val type: String,
    @SerialName("ratingMpaa") val ratingMpaa: String?,
    @SerialName("ratingAgeLimits") val ratingAgeLimits: String?,
    @SerialName("countries") val countries: List<Country>,
    @SerialName("genres") val genres: List<Genre>,
    @SerialName("startYear") val startYear: Int?,
    @SerialName("endYear") val endYear: Int?,
    @SerialName("serial") val isSerial: Boolean,
    @SerialName("shortFilm") val isShortFilm: Boolean,
    @SerialName("completed") val isCompleted: Boolean,
    @SerialName("hasImax") val hasImax: Boolean,
    @SerialName("has3D") val has3D: Boolean,
    @SerialName("lastSync") val lastSync: String
) {
    fun toUi() = Film(
        filmId = this.kinopoiskId,
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        year = this.year.toString(),
        filmLength = this.filmLength.toString(),
        countries = this.countries.map { com.tinkofflab.garbar.movies.listmovie.data.entity.Country(country = it.country) },
        genres = this.genres.map { com.tinkofflab.garbar.movies.listmovie.data.entity.Genre(genre = it.genre) },
        rating = "18+",
        ratingVoteCount = 22,
        posterUrl = this.posterUrl,
        posterUrlPreview = this.posterUrlPreview,
        ratingChange = "33",
        isRatingUp = false,
        isAfisha = false,
        description = description,
        isFavourite = false
    )
}

@kotlinx.serialization.Serializable
data class Country(
    @SerialName("country") val country: String
)

@kotlinx.serialization.Serializable
data class Genre(
    @SerialName("genre") val genre: String
)
