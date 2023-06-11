package com.bagusmerta.core.utils

import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.core.data.source.remote.movieeResponse.CastResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeDetailResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeItemResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeItemSearchResponse
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.core.domain.model.MovieeFavorite
import com.bagusmerta.core.domain.model.MovieeSearch

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
            posterPath = data.posterPath,
            genreId = data.genres
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

    fun mapListMovieeEntityToDomain(data: List<MovieeEntity>): List<MovieeFavorite> =
        data.map {
            MovieeFavorite(
                id = it.id,
                title = it.title,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                overview = it.overview,
                isFavorite = it.isFavorite,
                rating = it.rating,
                genre = it.genre
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
                rating = it.rating,
                genreId = mutableListOf()
            )
        }

    fun mapMovieeDomainToEntity(data: Moviee, genre: String): MovieeEntity =
            MovieeEntity(
                id = data.id,
                title = data.title,
                backdropPath = data.backdropPath,
                posterPath = data.posterPath,
                releaseDate = data.releaseDate,
                overview = data.overview,
                isFavorite = data.isFavorite,
                rating = data.rating,
                genre = genre
            )

    fun mapMovieeResponseToDomain(data: List<MovieeItemResponse>): List<Moviee> =
        data.map {
            Moviee(
                id = it.movieeId,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                overview = it.overview,
                releaseDate = it.releaseDate,
                title = it.movieeTitle,
                rating = it.rating,
                isFavorite = false,
                genreId = it.genreId
            )
        }

    fun mapMovieeSearchResponseToDomain(data: List<MovieeItemSearchResponse>): List<MovieeSearch> =
        data.map {
            MovieeSearch(
                id = it.movieeId,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                title = it.movieeTitle,
                rating = it.rating,
                isFavorite = false,
                genreId = it.genreId
            )
        }


}
