package com.bagusmerta.core.utils.testHelper

import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeResponse
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.DataMapper
import com.google.gson.Gson
import java.io.InputStreamReader

fun getDummyMoviee() =
        mutableListOf(
            Moviee(
                id = 297761,
                posterPath = "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
                title = "Suicide Squad",
                overview = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
                releaseDate = "2016-08-03",
                isFavorite = true,
                rating = 7.7
            ),
            Moviee(
                id = 324668,
                posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
                title = "Jason Bourne",
                overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
                backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                releaseDate = "2016-07-27",
                isFavorite = false,
                rating = 7.7
            )
        )

fun getDummyMovieeItemResponse() =
    mutableListOf(
        MovieeItemResponse(
            movieeId = 297761,
            posterPath = "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
            movieeTitle = "Suicide Squad",
            overview = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
            backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
            releaseDate = "2016-08-03",
            rating = 7.7
        ),
        MovieeItemResponse(
            movieeId = 324668,
            posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
            movieeTitle = "Jason Bourne",
            overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
            backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
            releaseDate = "2016-07-27",
            rating = 7.7
        )
    )


fun getSingleDummyMoviee() = Moviee(
    id = 324668,
    posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
    title = "Jason Bourne",
    overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
    backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
    releaseDate = "2016-07-27",
    isFavorite = false,
    rating = 7.7
)

fun <T> load(clss: Class<T>, file: String): T {
    val fixtureStreamReader = InputStreamReader(clss.classLoader?.getResourceAsStream(file))
    return Gson().fromJson(fixtureStreamReader, clss)
}

fun mapResponseToDomain(data: MovieeResponse): List<Moviee> {
    return DataMapper.mapListMovieeResponseToEntity(data.movieeResponse).let {
        DataMapper.mapListMovieeEntityToDomain(it)
    }
}