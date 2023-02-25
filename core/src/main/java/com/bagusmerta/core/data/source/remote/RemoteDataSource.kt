package com.bagusmerta.core.data.source.remote

import android.annotation.SuppressLint
import com.bagusmerta.core.data.source.remote.ApiConfig.MovieeService
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeDetailResponse
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.utility.ResultState
import com.bagusmerta.utility.flowableTransformerComputation
import com.bagusmerta.utility.singleTransformerComputation
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
            .compose(flowableTransformerComputation())
            .subscribe ({ response ->
                val data = response.movieeResponse
                res.onNext(if (data.isNotEmpty()) ResultState.Success(data) else ResultState.Empty)

            }, { error ->
                res.onNext(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            })

        return res.toFlowable(BackpressureStrategy.BUFFER)
    }


    fun searchMovies(query: String): Single<ResultState<List<MovieeItemResponse>>> {
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<List<MovieeItemResponse>>>()
        apiService.searchMovies(query)
            .compose(singleTransformerComputation())
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

    fun getTopRatedMovies(): Single<ResultState<List<MovieeItemResponse>>> {
        val mCompositeDisposable = CompositeDisposable()
        val res = SingleSubject.create<ResultState<List<MovieeItemResponse>>>()
        apiService.getTopRatedMovies()
            .compose(singleTransformerComputation())
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
            .compose(singleTransformerComputation())
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
            .compose(singleTransformerComputation())
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
            .compose(singleTransformerComputation())
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
            .compose(singleTransformerComputation())
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ response ->
                // Loop through genres to collect genre id
                response.genres?.forEach {
                    it.id?.let { genreId -> listGenreId.add(genreId) }
                }
                response.genreId = listGenreId

                res.onSuccess(if(response != null) ResultState.Success(response) else ResultState.Empty)
            }, { error ->
                res.onSuccess(ResultState.Error(error.message.toString()))
                Timber.e(error.toString())
            }).let(mCompositeDisposable::add)

        return res
    }

}