package com.bagusmerta.core.data.source.remote.ApiConfig


import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET

interface MovieeService {

    @GET("discover/movie")
    fun getListMovies(): Flowable<MovieeResponse>

}