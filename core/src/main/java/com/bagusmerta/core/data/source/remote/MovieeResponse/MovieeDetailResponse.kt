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
data class MovieeDetailResponse(
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

    @Json(name = "runtime")
    val runtime: Int?,

    @Json(name = "genres")
    var genres: List<Genre>?,

    var genreId: List<Int>?,

    @Json(name = "videos")
    var videos: Videos?,

    var keyVideo: String?

)

@JsonClass(generateAdapter = true)
data class Videos(
    @Json(name = "results")
    var listVideo: List<VideoInfo>?
)

@JsonClass(generateAdapter = true)
data class VideoInfo(
    @Json(name = "id")
    var id: String?,

    @Json(name = "key")
    var key: String?,

    @Json(name = "name")
    var name: String?,

    @Json(name = "official")
    var official: Boolean?,

    @Json(name = "published_at")
    var publishedAt: String?,

    @Json(name = "site")
    var site: String?,

    @Json(name = "size")
    var size: Int?,

    @Json(name = "type")
    var type: String?
)

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "id")
    var id: Int?,

    @Json(name = "name")
    var name: String?
)
