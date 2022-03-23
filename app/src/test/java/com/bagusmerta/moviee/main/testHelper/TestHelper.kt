package com.bagusmerta.moviee.main.testHelper

import com.bagusmerta.core.domain.model.Moviee
import com.google.gson.Gson
import java.io.InputStreamReader

fun getDummyResponse() =
        mutableListOf(
            Moviee(
                id = 297761,
                posterPath = "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
                title = "Suicide Squad",
                overview = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
                releaseDate = "2016-08-03",
                isFavorite = true
            ),
            Moviee(
                id = 324668,
                posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
                title = "Jason Bourne",
                overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
                backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                releaseDate = "2016-07-27",
                isFavorite = false
            )
        )


fun <T> load(clss: Class<T>, file: String): T {
    val fixtureStreamReader = InputStreamReader(clss.classLoader?.getResourceAsStream(file))
    return Gson().fromJson(fixtureStreamReader, clss)
}