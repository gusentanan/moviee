/*
 * Designed and developed by 2023 gusentanan (Bagus Merta)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bagusmerta.core.utils

import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.core.data.source.remote.movieeResponse.CastResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.Genre
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeDetailResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeItemResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeItemSearchResponse
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.core.domain.model.MovieeFavorite
import com.bagusmerta.core.domain.model.MovieeSearch
import com.bagusmerta.utility.GenreList

object DataMapper {

    fun mappingMovieGenreListFromId(id: List<Int>?): List<Genre> {
        if (id == null) { return emptyList() }
        val results = mutableListOf<Genre>()
        id.forEach { GenreList.moviesGenresMap.containsKey(it) && results.add(Genre(it, GenreList.moviesGenresMap[it]!!)) }
        return results
    }

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
            keyVideo = data.keyVideo,
            budget = data.budget,
            revenue = data.revenue,
            originalLanguage = data.originalLanguage,
            originalTitle = data.originalTitle,
            status = data.status,
            tagline = data.tagline,
            voteCount = data.voteCount,
            productionCountries = data.productionCountries?.get(0)?.countryName.toString()

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
