package com.bagusmerta.core.data.source.local

import com.bagusmerta.core.data.source.local.dao.MovieeDao
import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

class LocalDataSource(private val dao: MovieeDao) {

    fun getAllMovies(): Flowable<List<MovieeEntity>> = dao.getAllMovies()

    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<MovieeEntity>> = dao.getAllFavoriteMovies(isFavorite)

    fun insertMovieData(data: List<MovieeEntity>): Completable = dao.insertMovieData(data)

    fun checkFavoriteMovie(id: Int): Maybe<MovieeEntity> = dao.checkFavoriteMovies(id)

    fun setFavoriteMovie(data: MovieeEntity, isFavorite: Boolean): Single<Unit> {
        data.isFavorite = isFavorite
        return Single.fromCallable{ dao.updateFavoriteMovie(data) }
    }
}