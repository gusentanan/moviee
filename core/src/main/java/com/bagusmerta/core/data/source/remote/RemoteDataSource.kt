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
package com.bagusmerta.core.data.source.remote

import android.annotation.SuppressLint
import com.bagusmerta.core.data.source.remote.apiConfig.MovieeService
import com.bagusmerta.core.data.source.remote.movieeResponse.CastResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeDetailResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeItemResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeItemSearchResponse
import com.bagusmerta.utility.ResultState
import com.bagusmerta.utility.extensions.flowableTransformerIo
import com.bagusmerta.utility.extensions.singleTransformerIo
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.SingleSubject
import timber.log.Timber

class RemoteDataSource(private val apiService: MovieeService) {

    @SuppressLint("CheckResult")
    fun getAllMovies(): Flowable<ResultState<List<MovieeItemResponse>>> {
        val res = PublishSubject.create<ResultState<List<MovieeItemResponse>>>()
        apiService.getListMovies()
            .compose(flowableTransformerIo())
            .subscribe ({ response ->
                val data = response.movieeResponse
                res.onNext(if (data.isNotEmpty()) ResultState.Success(data) else ResultState.Empty)
            }, { error ->
                res.onNext(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            })
        return res.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun searchMovies(query: String): Single<ResultState<List<MovieeItemSearchResponse>>> {
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<List<MovieeItemSearchResponse>>>()
        apiService.searchMovies(query)
            .compose(singleTransformerIo())
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ response ->
                val data = response.movieeItemSearch
                res.onSuccess(if (data.isNotEmpty()) ResultState.Success(data) else ResultState.Empty)
            }, { error ->
                res.onSuccess(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return res
    }

    fun getTopRatedMovies(): Single<ResultState<List<MovieeItemResponse>>> {
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<List<MovieeItemResponse>>>()
        apiService.getTopRatedMovies()
            .compose(singleTransformerIo())
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ response ->
                val data = response.movieeResponse
                res.onSuccess(if (data.isNotEmpty()) ResultState.Success(data) else ResultState.Empty)
            }, { error ->
                res.onSuccess(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return res
    }

    fun getUpcomingMovies(): Single<ResultState<List<MovieeItemResponse>>> {
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<List<MovieeItemResponse>>>()
        apiService.getUpcomingMovies()
            .compose(singleTransformerIo())
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ response ->
                val data = response.movieeResponse
                res.onSuccess(if (data.isNotEmpty()) ResultState.Success(data) else ResultState.Empty)
            }, { error ->
                res.onSuccess(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return res
    }
    fun getNowPlayingMovies(): Single<ResultState<List<MovieeItemResponse>>> {
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<List<MovieeItemResponse>>>()
        apiService.getNowPlayingMovies()
            .compose(singleTransformerIo())
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ response ->
                val data = response.movieeResponse
                res.onSuccess(if (data.isNotEmpty()) ResultState.Success(data) else ResultState.Empty)
            }, { error ->
                res.onSuccess(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return res
    }

    fun getPopularMovies(): Single<ResultState<List<MovieeItemResponse>>> {
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<List<MovieeItemResponse>>>()
        apiService.getPopularMovies()
            .compose(singleTransformerIo())
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ response ->
                val data = response.movieeResponse
                res.onSuccess(if (data.isNotEmpty()) ResultState.Success(data) else ResultState.Empty)
            }, { error ->
                res.onSuccess(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return res
    }

    fun getDetailMovies(movie_id: Int): Single<ResultState<MovieeDetailResponse>>{
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<MovieeDetailResponse>>()
        val listGenreId = arrayListOf<Int>()
        apiService.getDetailMovies(movie_id)
            .compose(singleTransformerIo())
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ response ->
                // Loop through genres to collect genre id
                response.genres?.forEach {
                    it.id?.let { genreId -> listGenreId.add(genreId) }
                }
                response.genreId = listGenreId

                // loop through list of video to find one Trailer
                response.videos?.listVideo?.forEach {
                    if(it.type == "Trailer" && it.site == "YouTube"){
                        response.keyVideo = it.key
                        response.titleVideo = it.name

                    }
                }
                res.onSuccess(if(response != null) ResultState.Success(response) else ResultState.Empty)
            }, { error ->
                res.onSuccess(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return res
    }

    fun getMovieCast(movie_id: Int): Single<ResultState<List<CastResponse>>>{
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<List<CastResponse>>>()
        apiService.getMovieCast(movie_id)
            .compose(singleTransformerIo())
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ response ->
                val itemCast = response.movieCast
                res.onSuccess(if(itemCast.isNotEmpty()) ResultState.Success(itemCast) else ResultState.Empty)
            }, { error ->
                res.onSuccess(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return res
    }

    fun getSimilarMovies(movie_id: Int): Single<ResultState<List<MovieeItemResponse>>> {
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<List<MovieeItemResponse>>>()
        apiService.getSimilarMovies(movie_id)
            .compose(singleTransformerIo())
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ response ->
                val data = response.movieeResponse
                res.onSuccess(if(data.isNotEmpty()) ResultState.Success(data) else ResultState.Empty)
            }, { error ->
                res.onSuccess(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return res
    }

    fun getTrendingMovies(): Single<ResultState<List<MovieeItemResponse>>> {
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<List<MovieeItemResponse>>>()
        apiService.getTrendingMovies()
            .compose(singleTransformerIo())
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({response ->
                val data = response.movieeResponse
                res.onSuccess(if(data.isNotEmpty()) ResultState.Success(data) else ResultState.Empty)
            }, { error ->
                res.onSuccess(ResultState.Error(error.message.toString()))
                Timber.e(error.message)
            }).let(mCompositeDisposable::add)

        return res
    }



}
