package com.bagusmerta.core.domain.usecase

import com.bagusmerta.core.data.MovieeRepository
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.core.domain.model.MovieeFavorite
import com.bagusmerta.core.domain.model.MovieeSearch
import io.reactivex.Maybe
import io.reactivex.Single

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

    override fun searchMovies(query: String): Single<Resource<List<MovieeSearch>>> =
        repository.searchMovies(query)

    override fun getSimilarMovies(movieId: Int): Single<Resource<List<Moviee>>> =
        repository.getSimilarMovies(movieId)

    override fun getTrendingMovies(): Single<Resource<List<Moviee>>> =
        repository.getTrendingMovies()

    override fun getAllFavoriteMovies(isFavorite: Boolean): Single<Resource<List<MovieeFavorite>>> =
        repository.getAllFavoriteMovies(isFavorite)

    override fun setFavoriteMovies(data: Moviee, isFavorite: Boolean, genre: String): Single<Unit> =
        repository.setFavoriteMovies(data, isFavorite, genre)

    override fun checkFavoriteMovies(id: Int): Maybe<Moviee> =
        repository.checkFavoriteMovies(id)

    override fun deleteFavoriteMovies(id: Int): Single<Resource<String>> =
        repository.deleteFavoriteMovies(id)


}
