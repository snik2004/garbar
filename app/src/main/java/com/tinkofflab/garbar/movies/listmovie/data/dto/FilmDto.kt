package com.tinkofflab.garbar.movies.listmovie.data.dto

import com.tinkofflab.garbar.movies.listmovie.data.entity.Country
import com.tinkofflab.garbar.movies.listmovie.data.entity.Film
import com.tinkofflab.garbar.movies.listmovie.data.entity.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FilmDto(
    @SerialName("filmId") val filmId: Int,
    @SerialName("nameRu") val nameRu: String,
    @SerialName("nameEn") val nameEn: String?,
    @SerialName("year") val year: String,
    @SerialName("filmLength") val filmLength: String?,
    @SerialName("countries") val countries: List<CountryDto>,
    @SerialName("genres") val genres: List<GenreDto>,
    @SerialName("rating") val rating: String,
    @SerialName("ratingVoteCount") val ratingVoteCount: Int,
    @SerialName("posterUrl") val posterUrl: String,
    @SerialName("posterUrlPreview") val posterUrlPreview: String,
    @SerialName("ratingChange") val ratingChange: String?,
    @SerialName("isRatingUp") val isRatingUp: Boolean?,
    @SerialName("isAfisha") val isAfisha: Int,
) {
    fun toUi(): Film {
        return Film(
            filmId = this.filmId,
            nameRu = this.nameRu,
            nameEn = this.nameEn,
            year = this.year,
            filmLength = this.filmLength,
            countries = this.countries.map { Country(it.country) },
            genres = this.genres.map { Genre(it.genre) },
            rating = this.rating,
            ratingVoteCount = this.ratingVoteCount,
            posterUrl = this.posterUrl,
            posterUrlPreview = this.posterUrlPreview,
            ratingChange = this.ratingChange,
            isRatingUp = this.isRatingUp,
            isAfisha = this.isAfisha != 0,
            description = null
        )
    }
}
