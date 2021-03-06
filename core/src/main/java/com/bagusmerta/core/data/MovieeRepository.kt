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
    fun getUpcomingMovies(): Single<Resource<List<Moviee>>>
    fun getPopularMovies(): Single<Resource<List<Moviee>>>
    fun getNowPlayingMovies(): Single<Resource<List<Moviee>>>
    fun getTopRatedMovies(): Single<Resource<List<Moviee>>>
    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<Moviee>>
    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean): Single<Unit>
    fun searchMovies(query: String): Single<Resource<List<Moviee>>>
    fun checkFavoriteMovies(id: Int): Maybe<Moviee>

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

    override fun getUpcomingMovies(): Single<Resource<List<Moviee>>> {
        val res = SingleSubject.create<Resource<List<Moviee>>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.getUpcomingMovies()
            .doAfterTerminate{ mCompositeDisposable.clear() }
            .subscribe { value ->
                when (value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapResponseToDomain(value.data)))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

    override fun getPopularMovies(): Single<Resource<List<Moviee>>> {
        val res = SingleSubject.create<Resource<List<Moviee>>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.getPopularMovies()
            .doAfterTerminate{ mCompositeDisposable.clear() }
            .subscribe { value ->
                when (value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapResponseToDomain(value.data)))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

    override fun getNowPlayingMovies(): Single<Resource<List<Moviee>>> {
        val res = SingleSubject.create<Resource<List<Moviee>>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.getNowPlayingMovies()
            .doAfterTerminate{ mCompositeDisposable.clear() }
            .subscribe { value ->
                when (value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapResponseToDomain(value.data)))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

    override fun getTopRatedMovies(): Single<Resource<List<Moviee>>> {
        val res = SingleSubject.create<Resource<List<Moviee>>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.getTopRatedMovies()
            .doAfterTerminate{ mCompositeDisposable.clear() }
            .subscribe { value ->
                when (value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapResponseToDomain(value.data)))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

    override fun searchMovies(query: String): Single<Resource<List<Moviee>>> {
        val res = SingleSubject.create<Resource<List<Moviee>>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.searchMovies(query)
            .doAfterTerminate{ mCompositeDisposable.clear() }
            .subscribe { value ->
                when (value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapResponseToDomain(value.data)))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

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

    private fun mapResponseToDomain(movieResponse: List<MovieeItemResponse>): List<Moviee>{
        return DataMapper.mapListMovieeResponseToEntity(movieResponse).let {
            DataMapper.mapListMovieeEntityToDomain(it)
        }
    }

}