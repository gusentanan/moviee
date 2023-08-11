package com.bagusmerta.utility

enum class MovieeHomeFeed(val id: Int, val text: String, val subText: String) {
    NEWLY_MOVIES( 0, MovieeTextResource.NEWLY, MovieeSubTextResource.NEWLY),
    UPCOMING_MOVIES( 1, MovieeTextResource.UPCOMING, MovieeSubTextResource.UPCOMING),
    POPULAR_MOVIES( 2, MovieeTextResource.POPULAR, MovieeSubTextResource.POPULAR),
    TOP_RATED_MOVIES( 3, MovieeTextResource.TOP_RATED, MovieeSubTextResource.TOP_RATED),
    NOW_PLAYING_MOVIES( 4, MovieeTextResource.NOW_PLAYING, MovieeSubTextResource.NOW_PLAYING)
}

object MovieeTextResource {
    const val NEWLY: String = "Newly Movies"
    const val UPCOMING: String = "Upcoming Movies"
    const val POPULAR: String = "Popular Movies"
    const val TOP_RATED: String = "Top Rated Movies"
    const val NOW_PLAYING: String = "Now Playing Movies"
}

object MovieeSubTextResource {
    const val NEWLY: String = "Fresh and anticipated movies"
    const val POPULAR: String = "Discover the latest blockbuster"
    const val UPCOMING: String = "The ultimate movies experience"
    const val TOP_RATED: String = "Cinematic masterpiece lauded"
    const val NOW_PLAYING: String = "Thrilling options available"
}