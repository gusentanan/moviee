package com.bagusmerta.core.data

import android.annotation.SuppressLint
import com.bagusmerta.core.data.source.local.LocalDataSource
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.data.source.remote.RemoteDataSource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.DataMapper
import com.bagusmerta.core.utils.ResultState
import com.bagusmerta.core.utils.completableTransformerIo
import io.reactivex.Flowable
import io.reactivex.Single


interface MovieeRepository {
    fun getAllMovies(): Flowable<Resource<List<Moviee>>>
    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<Moviee>>
    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean): Single<Unit>
    fun getDetailMovies(id: Int): Flowable<Resource<Moviee>>
}

class MovieeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MovieeRepository {

    @SuppressLint("CheckResult")
    override fun getAllMovies(): Flowable<Resource<List<Moviee>>> =
        object : NetworkBoundResource<List<Moviee>, List<MovieeItemResponse>>() {
            override fun loadFromDB(): Flowable<List<Moviee>> {
                return localDataSource.getAllMovies().map { DataMapper.mapListMovieeEntityToDomain(it) }
            }

            override fun shouldFetch(data: List<Moviee>?): Boolean {
                return true
            }

            override fun createCall(): Flowable<ResultState<List<MovieeItemResponse>>> {
                return remoteDataSource.getAllMovies()
            }

            override fun saveCallResult(data: List<MovieeItemResponse>) {
                val movieeList = DataMapper.mapListMovieeResponseToEntity(data)
                localDataSource.insertMovieData(movieeList)
                    .compose(completableTransformerIo())
                    .subscribe()
            }

        }.asFlowable()


    override fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<Moviee>> {
        return localDataSource.getAllFavoriteMovies(isFavorite).map { DataMapper.mapListMovieeEntityToDomain(it) }
    }

    override fun setFavoriteMovies(data: Moviee, isFavorite: Boolean): Single<Unit> {
        val newData = DataMapper.mapMovieeDomainToEntity(data)
        return localDataSource.setFavoriteMovie(newData, isFavorite)
    }

    override fun getDetailMovies(id: Int): Flowable<Resource<Moviee>> =
        object: NetworkBoundResource <Moviee, MovieeItemResponse>() {
            override fun loadFromDB(): Flowable<Moviee> {
                return localDataSource.getDetailMovieData(id).map {
                    DataMapper.mapMovieeEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Moviee?): Boolean {
                return data == null
            }

            override fun createCall(): Flowable<ResultState<MovieeItemResponse>> {
                return remoteDataSource.getDetailMovieData(id)
            }

            override fun saveCallResult(data: MovieeItemResponse) {
                val newData = DataMapper.mapMovieeResponseToEntity(data)
                localDataSource.insertDetailMovieData(newData)
                    .compose(completableTransformerIo())
                    .subscribe()
            }

        }.asFlowable()


}