package com.bagusmerta.core.domain.model

data class MovieeDetail(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val releaseDate: String?,
    val backdropPath: String?,
    val posterPath: String?,
    val isFavorite: Boolean?,
    val rating: Double?,
    val genres: List<Int>?,
    val runtime: Int?
)

