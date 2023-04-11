package com.bagusmerta.core.data

import com.bagusmerta.core.data.source.local.LocalDataSource
import com.bagusmerta.core.data.source.remote.MovieeResponse.CastResponse
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeDetailResponse
import com.bagusmerta.core.data.source.remote.RemoteDataSource
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.core.domain.model.MovieeFavorite
import com.bagusmerta.core.utils.DataMapper
import com.bagusmerta.core.utils.DataMapper.mapMovieeResponseToDomain
import com.bagusmerta.utility.ResultState
import com.bagusmerta.utility.maybeTransformerIo
import com.bagusmerta.utility.singleTransformerIo
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.SingleSubject


interface MovieeRepository {
    fun getAllMovies(): Single<Resource<List<Moviee>>>
    fun getUpcomingMovies(): Single<Resource<List<Moviee>>>
    fun getPopularMovies(): Single<Resource<List<Moviee>>>
    fun getNowPlayingMovies(): Single<Resource<List<Moviee>>>
    fun getTopRatedMovies(): Single<Resource<List<Moviee>>>
    fun getDetailMovies(movieId: Int): Single<Resource<MovieeDetail>>
    fun getMovieCast(movieId: Int): Single<Resource<List<Cast>>>
    fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<MovieeFavorite>>
    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean, genre: String): Single<Unit>
    fun searchMovies(query: String): Single<Resource<List<Moviee>>>
    fun checkFavoriteMovies(id: Int): Maybe<Moviee>
    fun deleteFavoriteMovies(id: Int): Single<Resource<String>>
    fun getSimilarMovie(movieId: Int): Single<Resource<List<Moviee>>>

}

class MovieeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MovieeRepository {

    override fun getAllMovies(): Single<Resource<List<Moviee>>> {
        val res = SingleSubject.create<Resource<List<Moviee>>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.getAllMovies()
            .doAfterTerminate{ mCompositeDisposable.clear() }
            .subscribe { value ->
                when (value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapMovieeResponseToDomain(value.data)))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

    override fun getUpcomingMovies(): Single<Resource<List<Moviee>>> {
        val res = SingleSubject.create<Resource<List<Moviee>>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.getUpcomingMovies()
            .doAfterTerminate{ mCompositeDisposable.clear() }
            .subscribe { value ->
                when (value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapMovieeResponseToDomain(value.data)))
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
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapMovieeResponseToDomain(value.data)))
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
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapMovieeResponseToDomain(value.data)))
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
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapMovieeResponseToDomain(value.data)))
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
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapMovieeResponseToDomain(value.data)))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

    override fun getDetailMovies(movieId: Int): Single<Resource<MovieeDetail>> {
        val res =  SingleSubject.create<Resource<MovieeDetail>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.getDetailMovies(movieId)
            .doAfterTerminate{ mCompositeDisposable.clear() }
            .subscribe { value ->
                when (value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapDetailResponseToDomain(value.data, value.data.genreId)))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

    override fun getMovieCast(movieId: Int): Single<Resource<List<Cast>>> {
        val res = SingleSubject.create<Resource<List<Cast>>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.getMovieCast(movieId)
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe { value ->
                when(value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapCastMovieResponseToDomain(value.data)))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

    override fun getSimilarMovie(movieId: Int): Single<Resource<List<Moviee>>> {
        val res = SingleSubject.create<Resource<List<Moviee>>>()
        val mCompositeDisposable = CompositeDisposable()
        remoteDataSource.getSimilarMovie(movieId)
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe { value ->
                when(value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(mapMovieeResponseToDomain(value.data)))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

    override fun getAllFavoriteMovies(isFavorite: Boolean): Flowable<List<MovieeFavorite>> {
        return localDataSource.getAllFavoriteMovies(isFavorite).map { DataMapper.mapListMovieeEntityToDomain(it) }
    }

    override fun setFavoriteMovies(data: Moviee, isFavorite: Boolean, genre: String): Single<Unit> {
        val newData = DataMapper.mapMovieeDomainToEntity(data, genre)
        return localDataSource.setFavoriteMovie(newData, isFavorite)
            .compose(singleTransformerIo())
    }

    override fun checkFavoriteMovies(id: Int): Maybe<Moviee> {
        return localDataSource.checkFavoriteMovie(id)
            .compose(maybeTransformerIo())
            .map { DataMapper.mapMovieeEntityToDomain(it) }

    }

    override fun deleteFavoriteMovies(id: Int): Single<Resource<String>> {
        val res = SingleSubject.create<Resource<String>>()
        val mCompositeDisposable = CompositeDisposable()
        localDataSource.deleteFavoriteMovie(id)
            .subscribe { value ->
                when(value) {
                    is ResultState.Success -> res.onSuccess(Resource.Success(value.data))
                    is ResultState.Error -> res.onSuccess(Resource.Error(value.errorMessage))
                    is ResultState.Empty -> res.onSuccess(Resource.Empty)
                }
            }.let(mCompositeDisposable::add)

        return res
    }

    private fun mapDetailResponseToDomain(detailMovieResponse: MovieeDetailResponse, genreIds: List<Int>?): MovieeDetail {
        return DataMapper.mapDetailMovieeResponseToDomain(detailMovieResponse, genreIds)
    }

    private fun mapCastMovieResponseToDomain(castMovieResponse: List<CastResponse>): List<Cast>{
        return DataMapper.mapMovieCastResponseToDomain(castMovieResponse)
    }

}