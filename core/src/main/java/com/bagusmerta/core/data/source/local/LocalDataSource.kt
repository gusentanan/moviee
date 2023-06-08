package com.bagusmerta.core.data.source.local

import com.bagusmerta.core.data.source.local.dao.MovieeDao
import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.utility.ResultState
import com.bagusmerta.utility.completableTransformerIo
import com.bagusmerta.utility.flowableTransformerIo
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.SingleSubject
import timber.log.Timber

class LocalDataSource(private val dao: MovieeDao) {

    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<ResultState<List<MovieeEntity>>> {
        val mCompositeDisposable = CompositeDisposable()
        val result = PublishSubject.create<ResultState<List<MovieeEntity>>>()
        dao.getAllFavoriteMovies(isFavorite)
            .compose(flowableTransformerIo())
            .subscribe({ data ->
                if(data.isNotEmpty()) {
                    result.onNext(ResultState.Success(data))
                } else {
                    result.onNext(ResultState.Empty)
                }
            }, { error ->
                result.onNext(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return result.toFlowable(BackpressureStrategy.BUFFER)
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

    fun checkFavoriteMovie(id: Int): Maybe<MovieeEntity> = dao.checkFavoriteMovies(id)

    fun setFavoriteMovie(data: MovieeEntity, isFavorite: Boolean): Single<Unit> {
        data.isFavorite = isFavorite
        return Single.fromCallable{ dao.insertUpdateFavoriteMovie(data) }
    }

}