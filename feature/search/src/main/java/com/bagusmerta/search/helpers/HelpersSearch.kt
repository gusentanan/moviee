package com.bagusmerta.feature.search.helpers

import com.bagusmerta.core.data.source.remote.movieeResponse.Genre
import com.bagusmerta.utility.GenreList.moviesGenresMap
import java.text.SimpleDateFormat
import java.util.Locale

object HelpersSearch {
    fun mappingMovieGenreListFromId(id: List<Int>?): List<Genre> {
        if (id == null) { return emptyList() }
        val results = mutableListOf<Genre>()
        id.forEach { moviesGenresMap.containsKey(it) && results.add(Genre(it, moviesGenresMap[it]!!)) }
        return results
    }
}