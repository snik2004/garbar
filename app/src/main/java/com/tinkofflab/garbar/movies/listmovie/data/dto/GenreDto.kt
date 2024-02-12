package com.tinkofflab.garbar.movies.listmovie.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("genre") val genre: String,
)
