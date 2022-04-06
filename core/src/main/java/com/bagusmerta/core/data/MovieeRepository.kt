package com.bagusmerta.core.data

import android.annotation.SuppressLint
import com.bagusmerta.core.data.source.local.LocalDataSource
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.data.source.remote.RemoteDataSource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.DataMapper
import com.bagusmerta.utility.ResultState
import com.bagusmerta.utility.completableTransformerIo
import com.bagusmerta.utility.maybeTransformerIo
import com.bagusmerta.utility.singleTransformerIo
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.SingleSubject


interface MovieeRepository {
    fun getAllMovies(): Flowable<Resource<List<Moviee>>>
    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<Moviee>>
    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean): Single<Unit>
    fun checkFavoriteMovies(id: Int): Maybe<Moviee>
    fun searchMovies(query: String): Single<Resource<List<Moviee>>>
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
                return data == null || data.isEmpty()
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
            .compose(singleTransformerIo())
    }

    override fun checkFavoriteMovies(id: Int): Maybe<Moviee> {
        return localDataSource.checkFavoriteMovie(id)
            .compose(maybeTransformerIo())
            .map { DataMapper.mapMovieeEntityToDomain(it) }

    }

    override fun searchMovies(query: String): Single<Resource<List<Moviee>>> {
        val res = SingleSubject.create<Resource<List<Moviee>>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.searchMovies(query)
            .doAfterTerminate{ mCompositeDisposable.clear() }
            .subscribe { value ->
                when (value) {
                    is ResultState.Success -> {
                        val listMovie = DataMapper.mapListMovieeResponseToEntity(value.data).let {
                            DataMapper.mapListMovieeEntityToDomain(it)
                        }
                        res.onSuccess(Resource.Success(listMovie))
                    }
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

}