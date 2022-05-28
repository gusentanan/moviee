package com.bagusmerta.core

import com.bagusmerta.core.data.MovieeRepositoryImpl
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeResponse
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.domain.usecase.MovieeUseCaseImpl
import com.bagusmerta.core.utils.testHelper.*
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MovieeUseCaseTest {
    private val repository: MovieeRepositoryImpl = mock()
    private lateinit var useCase: MovieeUseCase

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        useCase = MovieeUseCaseImpl(repository)

        TrampolineRxSchedulers.start()
    }

    @Test
    fun `should return all movies when call getAllMovies`(){
        val expectedValue = Resource.Success(getDummyMoviee())

        whenever(repository.getAllMovies())
            .thenReturn(Flowable.just(expectedValue))

        useCase.getAllMovies()
        repository.getAllMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = Resource.Success(mapResponseToDomain(json))
                when(it){
                    is Resource.Success -> it.data.size == actualValue.data.size
                    else -> false
                }
            }.dispose()
        }

        verify(repository, atLeastOnce()).getAllMovies()
    }

    @Test
    fun `should return result of search movies when call moviee repository`(){
        val expectedValue = Resource.Success(getDummyMoviee())
        val title = "title"

        whenever(repository.searchMovies(title))
            .thenReturn(Single.just(expectedValue))

        useCase.searchMovies(title)
        repository.searchMovies(title).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = Resource.Success(mapResponseToDomain(json))
                when(it){
                    is Resource.Success -> it.data.size == actualValue.data.size
                    else -> false
                }
            }.dispose()
        }

        verify(repository, atLeastOnce()).searchMovies(title)
    }

    @Test
    fun `should return all favorite movies when call moviee repository`(){
        val expectedValue = getDummyMoviee()
        val state = true

        whenever(repository.getAllFavoriteMovies(state))
            .thenReturn(Flowable.just(expectedValue))

        useCase.getAllFavoriteMovies(state)
        repository.getAllFavoriteMovies(state).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = mapResponseToDomain(json)

                it.size == actualValue.size
            }.dispose()
        }

        verify(repository, atLeastOnce()).getAllFavoriteMovies(state)
    }

    @Test
    fun `should return success when call setFavoriteMovies from moviee repository`(){
        val data = getSingleDummyMoviee()
        val state = true

        whenever(repository.setFavoriteMovies(data, state))
            .thenReturn(Single.just(Unit))

        useCase.setFavoriteMovies(data, state)
        repository.setFavoriteMovies(data, state).test().apply {
            assertComplete()
            assertNoErrors()
            assertTerminated()
        }

        verify(repository, atLeastOnce()).setFavoriteMovies(data, state)
    }

    @Test
    fun `should return single movies when call checkFavoriteMovies from moviee repository`(){
        val id = 324668
        val expectedValue = getSingleDummyMoviee()

        whenever(repository.checkFavoriteMovies(id))
            .thenReturn(Maybe.just(expectedValue))

        useCase.checkFavoriteMovies(id)
        repository.checkFavoriteMovies(id).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = mapResponseToDomain(json)

                it.title == actualValue[1].title
            }.dispose()
        }
        verify(repository, atLeastOnce()).checkFavoriteMovies(id)
    }

    @Test
    fun `should return list of movies when call getUpcomingMovies`(){
        val expectedValue = Resource.Success(getDummyMoviee())

        whenever(repository.getUpcomingMovies())
            .thenReturn(Single.just(expectedValue))

        useCase.getUpcomingMovies()
        repository.getUpcomingMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = Resource.Success(mapResponseToDomain(json))
                when(it){
                    is Resource.Success -> it.data.size == actualValue.data.size
                    else -> false
                }
            }.dispose()
        }

        verify(repository, atLeastOnce()).getUpcomingMovies()
    }

    @Test
    fun `should return list of movies when call getPopularMovies`(){
        val expectedValue = Resource.Success(getDummyMoviee())

        whenever(repository.getPopularMovies())
            .thenReturn(Single.just(expectedValue))

        useCase.getPopularMovies()
        repository.getPopularMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = Resource.Success(mapResponseToDomain(json))
                when(it){
                    is Resource.Success -> it.data.size == actualValue.data.size
                    else -> false
                }
            }.dispose()
        }

        verify(repository, atLeastOnce()).getPopularMovies()
    }

    @Test
    fun `should return list of movies when call getNowPlayingMovies`(){
        val expectedValue = Resource.Success(getDummyMoviee())

        whenever(repository.getNowPlayingMovies())
            .thenReturn(Single.just(expectedValue))

        useCase.getNowPlayingMovies()
        repository.getNowPlayingMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = Resource.Success(mapResponseToDomain(json))
                when(it){
                    is Resource.Success -> it.data.size == actualValue.data.size
                    else -> false
                }
            }.dispose()
        }

        verify(repository, atLeastOnce()).getNowPlayingMovies()
    }

    @Test
    fun `should return list of movies when call getTopRatedMovies`(){
        val expectedValue = Resource.Success(getDummyMoviee())

        whenever(repository.getTopRatedMovies())
            .thenReturn(Single.just(expectedValue))

        useCase.getTopRatedMovies()
        repository.getTopRatedMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = Resource.Success(mapResponseToDomain(json))
                when(it){
                    is Resource.Success -> it.data.size == actualValue.data.size
                    else -> false
                }
            }.dispose()
        }

        verify(repository, atLeastOnce()).getTopRatedMovies()
    }

    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
    }

}