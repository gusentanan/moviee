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
