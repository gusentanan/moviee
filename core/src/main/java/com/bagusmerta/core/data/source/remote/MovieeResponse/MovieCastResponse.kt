package com.bagusmerta.core.data.source.remote.MovieeResponse

import com.google.gson.annotations.SerializedName

data class MovieCastResponse(
    @SerializedName("cast")
    var movieCast: List<CastResponse>
)

data class CastResponse(

    @SerializedName("character")
    var character: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("profile_path")
    var profilePicPath: String?
)
