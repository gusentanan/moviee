package com.bagusmerta.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.core.utils.Constants.CHECK_FAVORITE_MOVIES
import com.bagusmerta.core.utils.Constants.DELETE_FAVORITE_MOVIES
import com.bagusmerta.core.utils.Constants.GET_ALL_FAVORITE_MOVIES
import com.bagusmerta.core.utils.Constants.GET_ALL_MOVIES
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe


@Dao
interface MovieeDao {

    @Query(GET_ALL_MOVIES)
    fun getAllMovies(): Flowable<List<MovieeEntity>>

    @Query(GET_ALL_FAVORITE_MOVIES)
    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<MovieeEntity>>

    @Query(CHECK_FAVORITE_MOVIES)
    fun checkFavoriteMovies(movieId: Int): Maybe<MovieeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpdateFavoriteMovie(data: MovieeEntity)

    @Query(DELETE_FAVORITE_MOVIES)
    fun deleteFavoriteMovie(movieId: Int): Completable

}