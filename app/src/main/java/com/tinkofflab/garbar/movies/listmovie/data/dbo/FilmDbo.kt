package com.tinkofflab.garbar.movies.listmovie.data.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tinkofflab.garbar.movies.listmovie.data.entity.Country
import com.tinkofflab.garbar.movies.listmovie.data.entity.Film
import com.tinkofflab.garbar.movies.listmovie.data.entity.Genre

@Entity(tableName = "films")
data class FilmDbo(
    @PrimaryKey val filmId: Int,
    val nameRu: String,
    val nameEn: String?,
    val year: String,
    val filmLength: String?,
    val countries: List<CountryDbo>,
    val genres: List<GenreDbo>,
    val rating: String,
    val ratingVoteCount: Int,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingChange: String?,
    val isRatingUp: Boolean?,
    val isAfisha: Boolean,
    val isFavourite: Boolean = false,
    val description: String? = "",
) {
    fun toUi() = Film(
        filmId = this.filmId,
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        year = this.year,
        filmLength = this.filmLength,
        countries = this.countries.map { Country(country = it.country) },
        genres = this.genres.map { Genre(genre = it.genre) },
        rating = this.rating,
        ratingVoteCount = this.ratingVoteCount,
        posterUrl = this.posterUrl,
        posterUrlPreview = this.posterUrlPreview,
        ratingChange = this.ratingChange,
        isRatingUp = this.isRatingUp,
        isAfisha = this.isAfisha,
        isFavourite = this.isFavourite,
        description = this.description
    )

    companion object {
        fun fromUi(film: Film) = FilmDbo(
            filmId = film.filmId,
            nameRu = film.nameRu,
            nameEn = film.nameEn,
            year = film.year,
            filmLength = film.filmLength,
            countries = film.countries.map { CountryDbo(country = it.country) },
            genres = film.genres.map { GenreDbo(genre = it.genre) },
            rating = film.rating,
            ratingVoteCount = film.ratingVoteCount,
            posterUrl = film.posterUrl,
            posterUrlPreview = film.posterUrlPreview,
            ratingChange = film.ratingChange,
            isRatingUp = film.isRatingUp,
            isAfisha = film.isAfisha,
            isFavourite = film.isFavourite,
            description = film.description
        )
    }
}

@Entity(tableName = "country")
data class CountryDbo(
    @PrimaryKey val country: String,
)

@Entity(tableName = "genre")
data class GenreDbo(
    @PrimaryKey val genre: String,
)