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
package com.bagusmerta.core.data.source.remote.movieeResponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieeResponse(
    @Json(name = "results")
    val movieeResponse: List<MovieeItemResponse>
)

@JsonClass(generateAdapter = true)
data class MovieeItemResponse(
    @Json(name = "id")
    val movieeId: Int?,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "overview")
    val overview: String?,

    @Json(name = "release_date")
    val releaseDate: String?,

    @Json(name = "title")
    val movieeTitle: String?,

    @Json(name = "vote_average")
    val rating: Double?,

    @Json(name = "genre_ids")
    val genreId: List<Int>?

)
