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

import com.google.gson.annotations.SerializedName

data class MovieeDetailResponse(
    @SerializedName("id")
    val movieeId: Int?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("title")
    val movieeTitle: String?,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("genres")
    var genres: List<Genre>?,

    var genreId: List<Int>?,

    @SerializedName("videos")
    var videos: Videos?,

    var keyVideo: String?

)
data class Videos(
    @SerializedName("results")
    var listVideo: List<VideoInfo>?
)

data class VideoInfo(
    @SerializedName("id")
    var id: String?,

    @SerializedName("key")
    var key: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("official")
    var official: Boolean?,

    @SerializedName("published_at")
    var publishedAt: String?,

    @SerializedName("site")
    var site: String?,

    @SerializedName("size")
    var size: Int?,

    @SerializedName("type")
    var type: String?
)

data class Genre(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("name")
    var name: String?
)
