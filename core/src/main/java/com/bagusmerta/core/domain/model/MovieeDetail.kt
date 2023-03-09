package com.bagusmerta.core.domain.model

import com.bagusmerta.core.data.source.remote.MovieeResponse.VideoInfo

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
    val runtime: Int?,
    val keyVideo: String?
)



