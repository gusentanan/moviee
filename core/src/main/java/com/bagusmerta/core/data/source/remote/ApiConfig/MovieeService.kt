package com.bagusmerta.core.data.source.remote.ApiConfig


import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieCastResponse
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeDetailResponse
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{movie_id}")
    fun getDetailMovies(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String = "videos"
    ): Single<MovieeDetailResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("language") lang: String = "en-US"
    ): Single<MovieCastResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") lang: String? = "en-US",
        @Query("page") page: Int = 1
    ): Single<MovieeResponse>

}