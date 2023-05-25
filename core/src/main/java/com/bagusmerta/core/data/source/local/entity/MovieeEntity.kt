package com.bagusmerta.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviee_table")
data class MovieeEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "release_date")
    var releaseDate: String?,

    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String?,

    @ColumnInfo(name = "poster_path")
    var posterPath: String?,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean? = false,

    @ColumnInfo(name = "rating")
    var rating: Double?,

    @ColumnInfo(name= "genre")
    var genre: String?

)