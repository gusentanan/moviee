package com.bagusmerta.core.utils


sealed class MovieeFeature {
    sealed class CommonType: MovieeFeature() {
        object TopRated : CommonType()
        object NowPlaying : CommonType()
        object Popular : CommonType()
        object Upcoming : CommonType()
        object Newly : CommonType()
    }

    data class QueryType(val q: String): MovieeFeature()

    sealed class MovieeIdType : MovieeFeature() {
        data class CastType(val movieId: Int) : MovieeIdType()
        data class SimilarMovieType(val movieId: Int) : MovieeIdType()
    }
}



