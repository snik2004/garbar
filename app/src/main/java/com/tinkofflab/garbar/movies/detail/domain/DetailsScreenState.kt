package com.tinkofflab.garbar.movies.detail.domain

import com.tinkofflab.garbar.movies.listmovie.data.entity.Film

data class DetailsScreenState(
    val isLoading: Boolean = true,
    val film: Film? = null,
    val error: Throwable? = null,
    val isOfflineMode: Boolean = false,
)
