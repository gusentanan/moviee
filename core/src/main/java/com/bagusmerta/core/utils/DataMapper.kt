package com.bagusmerta.core.utils

import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.domain.model.Moviee

object DataMapper {
    fun mapListMovieeResponseToEntity(data: List<MovieeItemResponse>): List<MovieeEntity> =
        data.map {
            MovieeEntity(
                id = it.movieeId,
                title = it.movieeTitle,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                overview = it.overview,
                isFavorite = false,
                rating = it.rating
            )
        }

    fun mapListMovieeEntityToDomain(data: List<MovieeEntity>): List<Moviee> =
        data.map {
            Moviee(
                id = it.id,
                title = it.title,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                overview = it.overview,
                isFavorite = it.isFavorite,
                rating = it.rating
            )
        }

    fun mapMovieeEntityToDomain(data: MovieeEntity): Moviee =
        data.let {
            Moviee(
                id = it.id,
                title = it.title,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                isFavorite = it.isFavorite,
                overview = it.overview,
                rating = it.rating
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
                isFavorite = data.isFavorite,
                rating = data.rating
            )


}
