package com.bagusmerta.core.data.source.local

import com.bagusmerta.core.data.source.local.dao.MovieeDao
import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import io.reactivex.Completable
import io.reactivex.Flowable

class LocalDataSource(private val dao: MovieeDao) {

    fun getAllMovies(): Flowable<List<MovieeEntity>> = dao.getAllMovies()

    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<MovieeEntity>> = dao.getAllFavoriteMovies(isFavorite)

    fun insertMovieData(data: List<MovieeEntity>): Completable = dao.insertMovieData(data)

    fun setFavoriteMovie(data: MovieeEntity, isFavorite: Boolean){
        data.isFavorite = isFavorite
        dao.updateFavoriteMovie(data)
    }
}