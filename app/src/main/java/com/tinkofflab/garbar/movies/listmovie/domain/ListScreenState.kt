package com.tinkofflab.garbar.movies.listmovie.domain

import com.tinkofflab.garbar.movies.listmovie.data.entity.Film

data class ListScreenState (
    val isLoading: Boolean = true,
    val films: List<Film>,
    val bookmarks: List<Film> = listOf(),
    val error: Throwable? = null,
    val viewMode: ViewMode = ViewMode.All
)

enum class ViewMode {
    All,
    Bookmarks
}