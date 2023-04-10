package com.bagusmerta.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieeFavorite(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val releaseDate: String?,
    val backdropPath: String?,
    val posterPath: String?,
    val isFavorite: Boolean?,
    val rating: Double?,
    val genre: String?

): Parcelable