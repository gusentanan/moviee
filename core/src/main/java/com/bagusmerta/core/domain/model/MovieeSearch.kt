package com.bagusmerta.core.domain.model

data class MovieeSearch(
    val id: Int?,
    val title: String?,
    val releaseDate: String?,
    val backdropPath: String?,
    val isFavorite: Boolean?,
    val rating: Double?,
    val genreId: List<Int>?
)
