package com.bagusmerta.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Moviee(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val releaseDate: String?,
    val backdropPath: String?,
    val posterPath: String?,
    val isFavorite: Boolean?,
    val rating: Double?,
    val genreId: List<Int>?
): Parcelable

@Parcelize
data class HomeFeed(
    val feedTitle: String?,
    val feedSubHeader: String?,
    val listMovie: List<Moviee?>,
    val movieSection: Int?,
): Parcelable