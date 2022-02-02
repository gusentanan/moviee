package com.bagusmerta.core.utils

import com.bagusmerta.core.BuildConfig

object Constants {
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = BuildConfig.BASE_API
    const val IMAGE_URL = BuildConfig.POSTER_URL
    const val GET_ALL_MOVIES = "SELECT * FROM movie_table"
    const val GET_ALL_FAVORITE_MOVIES = "SELECT * FROM moviee_table WHERE isFavorite = :isFavorite"
}