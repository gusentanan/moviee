package com.bagusmerta.core.data.source.remote.ApiConfig


import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieeService {

    @GET("discover/movie")
    fun getListMovies(): Flowable<MovieeResponse>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String): Single<MovieeResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Single<MovieeResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(): Single<MovieeResponse>

    @GET("movie/popular")
    fun getPopularMovies(): Single<MovieeResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(): Single<MovieeResponse>

}