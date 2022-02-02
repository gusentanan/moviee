package com.bagusmerta.core.utils

import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.domain.model.Moviee

object DataMapper {
    fun mapMovieeResponseToEntity(data: List<MovieeItemResponse>): List<MovieeEntity> =
        data.map {
            MovieeEntity(
                id = it.movieeId,
                title = it.movieeTitle,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                overview = it.overview,
                isFavorite = false
            )
        }

    fun mapMovieeEntityToDomain(data: List<MovieeEntity>): List<Moviee> =
        data.map {
            Moviee(
                id = it.id,
                title = it.title,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                overview = it.overview,
                isFavorite = it.isFavorite
            )
        }

    fun mapMovieeDomainToEntity(data: Moviee): MovieeEntity =
            MovieeEntity(
                id = data.id,
                title = data.title,
                backdropPath = data.backdropPath,
                posterPath = data.posterPath,
                releaseDate = data.releaseDate,
                overview = data.overview,
                isFavorite = data.isFavorite
            )


}
