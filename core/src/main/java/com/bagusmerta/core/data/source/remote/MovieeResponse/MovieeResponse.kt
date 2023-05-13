package com.bagusmerta.core.data.source.remote.MovieeResponse

import com.google.gson.annotations.SerializedName

data class MovieeResponse(
    @SerializedName("results")
    val movieeResponse: List<MovieeItemResponse>
)

data class MovieeItemResponse(
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

    @SerializedName("genre_ids")
    val genreId: List<Int>?

)
