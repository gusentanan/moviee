package com.bagusmerta.core.utils

import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.domain.model.MovieeEntity

object DataMapper {
    fun mapMovieeResponseToDomain(data: List<MovieeItemResponse>?): List<MovieeEntity>? =
        data?.map {
            MovieeEntity(
                id = it.movieeId,
                title = it.movieeTitle,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                overview = it.overview,
            )
        }
    }
