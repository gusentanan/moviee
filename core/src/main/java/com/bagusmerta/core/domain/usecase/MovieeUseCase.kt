package com.bagusmerta.core.domain.usecase

import com.bagusmerta.core.data.MovieeRepository
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.singleTransformerIo
import io.reactivex.Flowable
import io.reactivex.Single

interface MovieeUseCase {
    fun getAllMovies(): Flowable<Resource<List<Moviee>>>
    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<Moviee>>
    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean): Single<Unit>
    fun searchMovies(query: String): Single<Resource<List<Moviee>>>

}

class MovieeUseCaseImpl(private val repository: MovieeRepository) : MovieeUseCase {

    override fun getAllMovies(): Flowable<Resource<List<Moviee>>> = repository.getAllMovies()

    override fun searchMovies(query: String): Single<Resource<List<Moviee>>> = repository.searchMovies(query)

    override fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<Moviee>> =
        repository.getAllFavoriteMovies(isFavorite)

    override fun setFavoriteMovies(data: Moviee, isFavorite: Boolean): Single<Unit> =
        repository.setFavoriteMovies(data, isFavorite)
            .compose(singleTransformerIo())

}