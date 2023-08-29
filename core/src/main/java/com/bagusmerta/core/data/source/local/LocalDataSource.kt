/*
 * Designed and developed by 2023 gusentanan (Bagus Merta)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bagusmerta.core.data.source.local

import com.bagusmerta.core.data.source.local.dao.MovieeDao
import com.bagusmerta.core.data.source.local.entity.MovieeEntity
import com.bagusmerta.utility.ResultState
import com.bagusmerta.utility.extensions.completableTransformerIo
import com.bagusmerta.utility.extensions.flowableTransformerIo
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
