package com.bagusmerta.feature.allmovie.helpers

import java.util.HashMap

object HelpersAllMovie {
    fun findMovieSection(key: Int): String? {
        movieSectionMap.let {
            return it[key]
        }
    }

    private val movieSectionMap: HashMap<Int, String> = hashMapOf(
        1 to "Newly Movies",
        2 to "Upcoming Movies",
        3 to "Popular Movies",
        4 to "Top Rated Movies",
        5 to "Now Playing Movies"
    )
}