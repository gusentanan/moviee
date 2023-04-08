package com.bagusmerta.core.domain.usecase

import com.bagusmerta.core.data.MovieeRepository
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface MovieeUseCase {
    fun getAllMovies(): Single<Resource<List<Moviee>>>
    fun getUpcomingMovies(): Single<Resource<List<Moviee>>>
    fun getPopularMovies(): Single<Resource<List<Moviee>>>
    fun getNowPlayingMovies(): Single<Resource<List<Moviee>>>
    fun getTopRatedMovies(): Single<Resource<List<Moviee>>>
    fun getDetailMovies(movieId: Int): Single<Resource<MovieeDetail>>
    fun getMovieCast(movieId: Int): Single<Resource<List<Cast>>>
    fun checkFavoriteMovies(id: Int): Maybe<Moviee>
    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<Moviee>>
    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean): Single<Unit>
    fun deleteFavoriteMovies(id: Int): Single<Resource<String>>
    fun searchMovies(query: String): Single<Resource<List<Moviee>>>
    fun getSimilarMovie(movieId: Int): Single<Resource<List<Moviee>>>

}

class MovieeUseCaseImpl(private val repository: MovieeRepository) : MovieeUseCase {

    override fun getAllMovies(): Single<Resource<List<Moviee>>> =
        repository.getAllMovies()

    override fun getUpcomingMovies(): Single<Resource<List<Moviee>>> =
        repository.getUpcomingMovies()

    override fun getPopularMovies(): Single<Resource<List<Moviee>>> =
        repository.getPopularMovies()

    override fun getNowPlayingMovies(): Single<Resource<List<Moviee>>> =
        repository.getNowPlayingMovies()

    override fun getTopRatedMovies(): Single<Resource<List<Moviee>>> =
        repository.getTopRatedMovies()

    override fun getDetailMovies(movieId: Int): Single<Resource<MovieeDetail>> =
        repository.getDetailMovies(movieId)

    override fun getMovieCast(movieId: Int): Single<Resource<List<Cast>>> =
        repository.getMovieCast(movieId)

    override fun searchMovies(query: String): Single<Resource<List<Moviee>>> =
        repository.searchMovies(query)

    override fun getSimilarMovie(movieId: Int): Single<Resource<List<Moviee>>> =
        repository.getSimilarMovie(movieId)

    override fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<Moviee>> =
        repository.getAllFavoriteMovies(isFavorite)

    override fun setFavoriteMovies(data: Moviee, isFavorite: Boolean): Single<Unit> =
        repository.setFavoriteMovies(data, isFavorite)

    override fun checkFavoriteMovies(id: Int): Maybe<Moviee> =
        repository.checkFavoriteMovies(id)

    override fun deleteFavoriteMovies(id: Int): Single<Resource<String>> =
        repository.deleteFavoriteMovies(id)


}