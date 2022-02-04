package com.bagusmerta.core.data

import android.annotation.SuppressLint
import com.bagusmerta.core.data.source.local.LocalDataSource
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.data.source.remote.RemoteDataSource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.*
import io.reactivex.Flowable



interface MovieeRepository {
    fun getAllMovies(): Flowable<Resource<List<Moviee>>>
    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<Moviee>>
    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean)
}

class MovieeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MovieeRepository {

    @SuppressLint("CheckResult")
    override fun getAllMovies(): Flowable<Resource<List<Moviee>>> =
        object : NetworkBoundResource<List<Moviee>, List<MovieeItemResponse>>() {
            override fun loadFromDB(): Flowable<List<Moviee>> {
                return localDataSource.getAllMovies().map { DataMapper.mapMovieeEntityToDomain(it) }
            }

            override fun shouldFetch(data: List<Moviee>?): Boolean {
                return true
            }

            override fun createCall(): Flowable<ResultState<List<MovieeItemResponse>>> {
                return remoteDataSource.getAllMovies()
            }

            override fun saveCallResult(data: List<MovieeItemResponse>) {
                val movieeList = DataMapper.mapMovieeResponseToEntity(data)
                localDataSource.insertMovieData(movieeList)
                    .compose(completeableTransformerIo())
                    .subscribe()
            }

        }.asFlowable()


    override fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<Moviee>> {
        return localDataSource.getAllFavoriteMovies(isFavorite).map { DataMapper.mapMovieeEntityToDomain(it) }
    }

    override fun setFavoriteMovies(data: Moviee, isFavorite: Boolean) {
        val newData = DataMapper.mapMovieeDomainToEntity(data)
        localDataSource.setFavoriteMovie(newData, isFavorite)
    }

}