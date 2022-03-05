package com.bagusmerta.core.data.source.local.dao

import androidx.room.*
import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.core.utils.Constants.GET_ALL_FAVORITE_MOVIES
import com.bagusmerta.core.utils.Constants.GET_ALL_MOVIES
import io.reactivex.Completable
import io.reactivex.Flowable


@Dao
interface MovieeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieData(data: List<MovieeEntity>): Completable

    @Query(GET_ALL_MOVIES)
    fun getAllMovies(): Flowable<List<MovieeEntity>>

    @Query(GET_ALL_FAVORITE_MOVIES)
    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<MovieeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavoriteMovie(data: MovieeEntity)

}