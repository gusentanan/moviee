package com.bagusmerta.core.data.source.remote.MovieeResponse

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

    var genreId: List<Int>?

)

data class Genre(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("name")
    var name: String?
)
