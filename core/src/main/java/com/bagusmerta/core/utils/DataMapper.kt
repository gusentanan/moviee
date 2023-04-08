package com.bagusmerta.core.utils

import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.core.data.source.remote.MovieeResponse.CastResponse
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeDetailResponse
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail

object DataMapper {

    fun mapMovieCastResponseToDomain(data: List<CastResponse>): List<Cast> =
        data.map {
            Cast(
                character = it.character,
                name = it.name,
                profilePicPath = it.profilePicPath
            )
        }

    fun mapMovieDetailToMoviee(data: MovieeDetail): Moviee =
        Moviee(
            id = data.id,
            title = data.title,
            overview = data.overview,
            releaseDate = data.releaseDate,
            backdropPath = data.backdropPath,
            isFavorite = data.isFavorite,
            rating = data.rating,
            posterPath = data.posterPath
        )

    fun mapDetailMovieeResponseToDomain(data: MovieeDetailResponse, genreIds: List<Int>?): MovieeDetail =
        MovieeDetail(
            id = data.movieeId,
            title = data.movieeTitle,
            overview = data.overview,
            releaseDate = data.releaseDate,
            backdropPath = data.backdropPath,
            posterPath = data.posterPath,
            isFavorite = false,
            rating = data.rating,
            genres = genreIds,
            runtime = data.runtime,
            keyVideo = data.keyVideo
        )

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
