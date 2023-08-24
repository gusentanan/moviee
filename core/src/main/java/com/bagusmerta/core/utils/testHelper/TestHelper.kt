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
package com.bagusmerta.core.utils.testHelper

import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.core.data.source.remote.movieeResponse.CastResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.Genre
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeDetailResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeItemResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeItemSearchResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.ProductionCountries
import com.bagusmerta.core.data.source.remote.movieeResponse.VideoInfo
import com.bagusmerta.core.data.source.remote.movieeResponse.Videos
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.buffer
import okio.source
import java.io.InputStreamReader

fun getDummyEntityMoviee() =
        mutableListOf(
            MovieeEntity(
                id = 297761,
                posterPath = "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
                title = "Suicide Squad",
                overview = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
                releaseDate = "2016-08-03",
                isFavorite = true,
                rating = 7.7,
                genre = "Action"
            ),
            MovieeEntity(
                id = 324668,
                posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
                title = "Jason Bourne",
                overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
                backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                releaseDate = "2016-07-27",
                isFavorite = false,
                rating = 7.7,
                genre = "Sci-fi"
            )
        )

fun getDummyMovieeItemResponse() =
    mutableListOf(
        MovieeItemResponse(
            movieeId = 297761,
            posterPath = "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
            movieeTitle = "Suicide Squad",
            overview = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
            backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
            releaseDate = "2016-08-03",
            rating = 7.7,
            genreId = mutableListOf(2,3,5)
        ),
        MovieeItemResponse(
            movieeId = 324668,
            posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
            movieeTitle = "Jason Bourne",
            overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
            backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
            releaseDate = "2016-07-27",
            rating = 7.7,
            genreId = mutableListOf(2,6,4)
        )
    )

fun getDummySearchResponse() =
    mutableListOf(
        MovieeItemSearchResponse(
            movieeId = 297761,
            movieeTitle = "Suicide Squad",
            backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
            releaseDate = "2016-08-03",
            rating = 7.7,
            genreId = mutableListOf(2,3,5)
        ),
        MovieeItemSearchResponse(
            movieeId = 324668,
            movieeTitle = "Jason Bourne",
            backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
            releaseDate = "2016-07-27",
            rating = 7.7,
            genreId = mutableListOf(2,6,4)
        )
    )


fun getSingleDummyMoviee() = MovieeDetailResponse(
    movieeId = 324668,
    posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
    movieeTitle = "Jason Bourne",
    overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
    backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
    releaseDate = "2016-07-27",
    rating = 7.7,
    genres = mutableListOf(
        Genre(
            id = 28,
            name = "Action"
        ),
        Genre(
            id = 12,
            name = "Adventure"
        )
    ),
    runtime = 115,
    videos = Videos(
        mutableListOf(
            VideoInfo(
                id = "643d18eb4d67910469d839d9",
                key = "Wk-MeF0ngVI",
                name = "The SWAT Chase Extended PreviewOfficial Trailer Jason Bourne",
                official = true,
                publishedAt = "2023-04-13T17:00:11.000Z",
                site = "YouTube",
                size = 720,
                type = "Clip"
            ),
        )
    ),
    genreId = mutableListOf(1,2,3),
    keyVideo = "Wk-MeF0ngVI",
    budget = 2000.0000,
    revenue = 201.210,
    originalTitle = "Jason Bourne",
    originalLanguage = "English",
    status = "Released",
    tagline = "Serious Agent",
    voteCount = 20000,
    productionCountries = mutableListOf(
        ProductionCountries(
            countryName = "United States of America"
        )
    )
)

fun getDummyCastResponse() = mutableListOf(
    CastResponse(
        character = "Spiderman - Peter Parker",
        name = "Tom Holland",
        profilePicPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg"
    ),
    CastResponse(
        character = "Iron Man - Tony Stark",
        name = "Robert Downey Junior",
        profilePicPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg"
    )
)

fun <T> load(clss: Class<T>, file: String): T? {
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val jsonAdapter: JsonAdapter<T> = moshi.adapter(clss)

    val fixtureStreamReader = clss.classLoader?.getResourceAsStream(file)?.source()?.buffer() ?: throw IllegalArgumentException("File not found: $file")
    val jsonReader = JsonReader.of(fixtureStreamReader)
    return jsonAdapter.fromJson(jsonReader)
}
