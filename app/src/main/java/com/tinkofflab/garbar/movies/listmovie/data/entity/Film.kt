package com.tinkofflab.garbar.movies.listmovie.data.entity

data class Film(
    val filmId: Int,
    val nameRu: String,
    val nameEn: String?,
    val year: String,
    val filmLength: String?,
    val countries: List<Country>,
    val genres: List<Genre>,
    val rating: String,
    val ratingVoteCount: Int,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingChange: String?,
    val isRatingUp: Boolean?,
    val isAfisha: Boolean,
    val description: String?,
    val isFavourite: Boolean = false,
)


