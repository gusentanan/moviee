package com.bagusmerta.core

import com.bagusmerta.core.data.MovieeRepository
import com.bagusmerta.core.data.MovieeRepositoryImpl
import com.bagusmerta.core.data.source.local.LocalDataSource
import com.bagusmerta.core.data.source.remote.RemoteDataSource
import com.bagusmerta.core.utils.testHelper.TrampolineRxSchedulers
import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

class MovieeRepositoryTest {
    private val remoteDataSource: RemoteDataSource = mock()
    private val localDataSource: LocalDataSource = mock()
    private lateinit var repository: MovieeRepository

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        repository = MovieeRepositoryImpl(remoteDataSource, localDataSource)

        TrampolineRxSchedulers.start()
    }

//  COLLAPSE THE UNIT TEST DUE TO CHANGE OF THE MODEL LAYER

//    @Test
//    fun `should return list movies when call getAllMovies - remoteDataSource`(){
//        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())
//
//        whenever(remoteDataSource.getAllMovies())
//            .thenReturn(Flowable.just(expectedValue))
//
//        remoteDataSource.getAllMovies().test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertValue {
//                val json = load(MovieeResponse::class.java, "response/movie_response.json")
//                val actualValue = Resource.Success(mapResponseToDomain(json))
//
//                when(it){
//                    is ResultState.Success -> it.data.size == actualValue.data.size
//                    else -> false
//                }
//            }.dispose()
//        }
//
//        verify(remoteDataSource, atLeastOnce()).getAllMovies()
//    }
//
//    @Test
//    fun `should return list movies when call localDataSource`(){
//        val expectedValue = getDummyMoviee().let {
//            it.map { item -> DataMapper.mapMovieeDomainToEntity(item) }
//        }
//
//        whenever(localDataSource.getAllMovies())
//            .thenReturn(Flowable.just(expectedValue))
//
//        repository.getAllMovies()
//        localDataSource.getAllMovies().test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertValue {
//                val json = load(MovieeResponse::class.java, "response/movie_response.json")
//                val actualValue = json.let { data ->
//                    DataMapper.mapListMovieeResponseToEntity(data.movieeResponse)
//                }
//
//                it.size == actualValue.size
//            }.dispose()
//        }
//        verify(localDataSource, atLeastOnce()).getAllMovies()
//    }
//
//    @Test
//    fun `should return true when save data movies to localDataSource`(){
//        val data = DataMapper.mapListMovieeResponseToEntity(getDummyMovieeItemResponse())
//
//        whenever(localDataSource.insertMovieData(data))
//            .thenReturn(Completable.complete())
//
//        localDataSource.insertMovieData(data).test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertTerminated()
//        }
//
//        verify(localDataSource, atLeastOnce()).insertMovieData(data)
//    }
//
//
//    @Test
//    fun `should return list favorite movies when call localDataSource`(){
//        val expectedValue = getDummyMoviee().let {
//            it.map { item -> DataMapper.mapMovieeDomainToEntity(item) }
//        }
//        val state = true
//
//        whenever(localDataSource.getAllFavoriteMovies(state))
//            .thenReturn(Flowable.just(expectedValue))
//
//        repository.getAllFavoriteMovies(state)
//        localDataSource.getAllFavoriteMovies(state).test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertValue {
//                val json = load(MovieeResponse::class.java, "response/movie_response.json")
//                val actualValue = DataMapper.mapListMovieeResponseToEntity(json.movieeResponse)
//
//                it.size == actualValue.size
//            }.dispose()
//        }
//        verify(localDataSource, atLeastOnce()).getAllFavoriteMovies(state)
//    }
//
//    @Test
//    fun `should return success when call setFavoriteMovies`(){
//        val data = DataMapper.mapMovieeDomainToEntity(getSingleDummyMoviee())
//        val state = true
//
//        whenever(localDataSource.setFavoriteMovie(data, state))
//            .thenReturn(Single.just(Unit))
//
//        repository.setFavoriteMovies(getSingleDummyMoviee(), state)
//        localDataSource.setFavoriteMovie(data, state).test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertTerminated()
//        }
//
//        verify(localDataSource, atLeastOnce()).setFavoriteMovie(data, state)
//    }
//
//    @Test
//    fun `should return single movie when call checkFavoriteMovies`(){
//        val expectedValue = DataMapper.mapMovieeDomainToEntity(getSingleDummyMoviee())
//        val id = 324668
//
//        whenever(localDataSource.checkFavoriteMovie(id))
//            .thenReturn(Maybe.just(expectedValue))
//
//        repository.checkFavoriteMovies(id)
//        localDataSource.checkFavoriteMovie(id).test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertValue {
//                val json = load(MovieeResponse::class.java, "response/movie_response.json")
//                val actualValue = DataMapper.mapListMovieeResponseToEntity(json.movieeResponse)
//
//                it.title == actualValue[1].title
//            }.dispose()
//        }
//        verify(localDataSource, atLeastOnce()).checkFavoriteMovie(id)
//    }
//
//    @Test
//    fun `should return list movies when call searchMovies`(){
//        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())
//        val title = "title"
//
//        whenever(remoteDataSource.searchMovies(title))
//            .thenReturn(Single.just(expectedValue))
//
//        repository.searchMovies(title)
//        remoteDataSource.searchMovies(title).test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertValue {
//                val json = load(MovieeResponse::class.java, "response/movie_response.json")
//                val actualValue = json.movieeResponse
//
//                when(it){
//                    is ResultState.Success -> it.data.size == actualValue.size
//                    else -> false
//                }
//            }.dispose()
//        }
//        verify(remoteDataSource, atLeastOnce()).searchMovies(title)
//    }
//
//    @Test
//    fun `should return list movies when call getTopRatedMovies - remoteDataSource`(){
//        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())
//
//        whenever(remoteDataSource.getTopRatedMovies())
//            .thenReturn(Single.just(expectedValue))
//
//        remoteDataSource.getTopRatedMovies().test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertValue {
//                val json = load(MovieeResponse::class.java, "response/movie_response.json")
//                val actualValue = Resource.Success(mapResponseToDomain(json))
//
//                when(it){
//                    is ResultState.Success -> it.data.size == actualValue.data.size
//                    else -> false
//                }
//            }.dispose()
//        }
//
//        verify(remoteDataSource, atLeastOnce()).getTopRatedMovies()
//    }
//
//    @Test
//    fun `should return list movies when call getUpcomingMovies - remoteDataSource`(){
//        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())
//
//        whenever(remoteDataSource.getUpcomingMovies())
//            .thenReturn(Single.just(expectedValue))
//
//        remoteDataSource.getUpcomingMovies().test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertValue {
//                val json = load(MovieeResponse::class.java, "response/movie_response.json")
//                val actualValue = Resource.Success(mapResponseToDomain(json))
//
//                when(it){
//                    is ResultState.Success -> it.data.size == actualValue.data.size
//                    else -> false
//                }
//            }.dispose()
//        }
//
//        verify(remoteDataSource, atLeastOnce()).getUpcomingMovies()
//    }
//
//    @Test
//    fun `should return list movies when call getNowPlayingMovies - remoteDataSource`(){
//        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())
//
//        whenever(remoteDataSource.getNowPlayingMovies())
//            .thenReturn(Single.just(expectedValue))
//
//        remoteDataSource.getNowPlayingMovies().test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertValue {
//                val json = load(MovieeResponse::class.java, "response/movie_response.json")
//                val actualValue = Resource.Success(mapResponseToDomain(json))
//
//                when(it){
//                    is ResultState.Success -> it.data.size == actualValue.data.size
//                    else -> false
//                }
//            }.dispose()
//        }
//
//        verify(remoteDataSource, atLeastOnce()).getNowPlayingMovies()
//    }
//
//    @Test
//    fun `should return list movies when call getPopularMovies - remoteDataSource`(){
//        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())
//
//        whenever(remoteDataSource.getPopularMovies())
//            .thenReturn(Single.just(expectedValue))
//
//        remoteDataSource.getPopularMovies().test().apply {
//            assertComplete()
//            assertNoErrors()
//            assertValue {
//                val json = load(MovieeResponse::class.java, "response/movie_response.json")
//                val actualValue = Resource.Success(mapResponseToDomain(json))
//
//                when(it){
//                    is ResultState.Success -> it.data.size == actualValue.data.size
//                    else -> false
//                }
//            }.dispose()
//        }
//
//        verify(remoteDataSource, atLeastOnce()).getPopularMovies()
//    }

    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
    }
}