package com.bagusmerta.core.data.source.remote.MovieeResponse

import com.google.gson.annotations.SerializedName

data class MovieeSearchResponse(
    @SerializedName("results")
    val movieeItemSearch: List<MovieeItemSearchResponse>
)

data class MovieeItemSearchResponse(
    @SerializedName("id")
    val movieeId: Int?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("title")
    val movieeTitle: String?,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("genre_ids")
    val genreId: List<Int>?
)
