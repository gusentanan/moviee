package com.bagusmerta.core.data.source.local

import com.bagusmerta.core.data.source.local.dao.MovieeDao
import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.utility.ResultState
import com.bagusmerta.utility.completableTransformerIo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.SingleSubject
import timber.log.Timber

class LocalDataSource(private val dao: MovieeDao) {

    fun getAllMovies(): Flowable<List<MovieeEntity>> = dao.getAllMovies()

    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<MovieeEntity>> = dao.getAllFavoriteMovies(isFavorite)

    fun insertMovieData(data: List<MovieeEntity>): Completable = dao.insertMovieData(data)

    fun checkFavoriteMovie(id: Int): Maybe<MovieeEntity> = dao.checkFavoriteMovies(id)

    fun setFavoriteMovie(data: MovieeEntity, isFavorite: Boolean): Single<Unit> {
        data.isFavorite = isFavorite
        return Single.fromCallable{ dao.updateFavoriteMovie(data) }

    }

    fun deleteFavoriteMovie(id: Int): Single<ResultState<String>> {
        val mCompositeDisposable = CompositeDisposable()
        val result = SingleSubject.create<ResultState<String>>()
        dao.deleteFavoriteMovie(id)
            .compose(completableTransformerIo())
            .subscribe({
                result.onSuccess(ResultState.Success("Delete Successfully Performed"))
            }, { error ->
                result.onSuccess(ResultState.Error("Delete Failed: ${error.message.toString()}"))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return result
    }
}