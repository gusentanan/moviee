package com.bagusmerta.core.domain.usecase

import com.bagusmerta.core.data.MovieeRepository
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.utils.ResultState
import io.reactivex.Flowable

interface MovieeUseCase {
    fun getAllMovies(): Flowable<ResultState<List<MovieeItemResponse>>>
}

class MovieeUseCaseImpl(private val repository: MovieeRepository): MovieeUseCase {

    override fun getAllMovies(): Flowable<ResultState<List<MovieeItemResponse>>> {
        TODO("Not yet implemented")
    }

}