package com.tinkofflab.garbar.movies.listmovie.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmsResponse(
    @SerialName("pagesCount") val pagesCount: Int,
    @SerialName("films") val films: List<FilmDto>,
)
