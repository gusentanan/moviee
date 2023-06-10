package com.bagusmerta.feature.search.helpers

import com.bagusmerta.core.data.source.remote.movieeResponse.Genre
import java.text.SimpleDateFormat
import java.util.HashMap
import java.util.Locale

object HelpersSearch {
    fun formatMediaDate(date: String?): String? {
        if(!date.isNullOrEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return sdf.parse(date)
                ?.let { SimpleDateFormat("yyyy", Locale.getDefault()).format(it) }
        } else {
            return "Unknown"
        }
    }

    fun mappingMovieGenreListFromId(id: List<Int>?): List<Genre> {
        if (id == null) {
            return emptyList()
        }
        val results = mutableListOf<Genre>()
        id.forEach {
            moviesGenresMap.containsKey(it) && results.add(Genre(it, moviesGenresMap[it]!!))
        }
        return results
    }

    private val moviesGenresMap: HashMap<Int, String> = hashMapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western",
    )

}