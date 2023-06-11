package com.bagusmerta.moviee.helpers

import com.bagusmerta.core.data.source.remote.movieeResponse.Genre
import com.bagusmerta.utility.GenreList.moviesGenresMap

object Helpers {
    fun mappingMovieGenreListFromId(id: List<Int>?): List<Genre> {
        if (id == null) { return emptyList() }
        val results = mutableListOf<Genre>()
        id.forEach { moviesGenresMap.containsKey(it) && results.add(Genre(it, moviesGenresMap[it]!!)) }
        return results
    }
}