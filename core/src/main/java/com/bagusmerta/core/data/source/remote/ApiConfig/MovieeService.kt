package com.bagusmerta.core.data.source.remote.ApiConfig


import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieeService {

    @GET("discover/movie")
    fun getListMovies(): Flowable<MovieeResponse>

    @GET("movie/{id}")
    fun getDetailMovies(@Path("id") movieId: Int): Flowable<MovieeItemResponse>

}