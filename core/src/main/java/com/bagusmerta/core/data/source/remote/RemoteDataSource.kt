package com.bagusmerta.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.bagusmerta.core.data.source.remote.ApiConfig.MovieeService
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.utils.ResultState
import com.bagusmerta.core.utils.flowableTransformerComputation
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

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
                Log.e("RemoteDataSource", error.toString())
            })

        return res.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getDetailMovieData(id: Int): Flowable<ResultState<MovieeItemResponse>> {
        val res = PublishSubject.create<ResultState<MovieeItemResponse>>()
        apiService.getDetailMovies(id)
            .compose(flowableTransformerComputation())
            .subscribe({ response ->
                val data = response.let {
                    MovieeItemResponse(
                        movieeId = it.movieeId,
                        movieeTitle = it.movieeTitle,
                        overview = it.overview,
                        backdropPath = it.backdropPath,
                        posterPath = it.posterPath,
                        releaseDate = it.releaseDate
                    )}
                res.onNext(ResultState.Success(data))
            }, { error ->
                res.onNext(ResultState.Error(error.message.toString()))
                Log.e("RemoteDataSource: Detail", error.toString())
            })

        return res.toFlowable(BackpressureStrategy.BUFFER)
    }

}