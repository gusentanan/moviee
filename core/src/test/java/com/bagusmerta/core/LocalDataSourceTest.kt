package com.bagusmerta.core

import com.bagusmerta.core.data.source.local.LocalDataSource
import com.bagusmerta.core.utils.testHelper.TrampolineRxSchedulers
import com.bagusmerta.core.utils.testHelper.getDummyEntityMoviee
import com.bagusmerta.utility.ResultState
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalDataSourceTest {
    private val localDataSource: LocalDataSource = mock()

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        TrampolineRxSchedulers.start()
    }

    @Test
    fun `localDataSource-getAllFavoriteMovies = should return a list of movies`(){
        val expectedValue = ResultState.Success(getDummyEntityMoviee())

        whenever(localDataSource.getAllFavoriteMovies(true))
            .thenReturn(Flowable.just(expectedValue))

        localDataSource.getAllFavoriteMovies(true).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val actualValue = getDummyEntityMoviee()

                when(it){
                    is ResultState.Success -> {
                        it.data.size == actualValue.size
                    }
                    else -> false
                }
            }.dispose()
        }

        verify(localDataSource, atLeastOnce()).getAllFavoriteMovies(true)
    }

    @Test
    fun `localDataSource-getAllFavoriteMovies = should return Empty`(){
        val expectedValue = ResultState.Empty

        whenever(localDataSource.getAllFavoriteMovies(true))
            .thenReturn(Flowable.just(expectedValue))

        localDataSource.getAllFavoriteMovies(true).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val actualValue = ResultState.Empty
                when(it){
                    is ResultState.Empty -> {
                        it == actualValue
                    }
                    else -> false
                }
            }.dispose()
        }

        verify(localDataSource, atLeastOnce()).getAllFavoriteMovies(true)
    }

    @Test
    fun `localDataSource-deleteFavoriteMovie = should return Success Message`(){
        val expectedValue = ResultState.Success("Delete Successfully Performed")
        val movieId = 123

        whenever(localDataSource.deleteFavoriteMovie(movieId))
            .thenReturn(Single.just(expectedValue))

        localDataSource.deleteFavoriteMovie(movieId).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val actualValue = ResultState.Success("Delete Successfully Performed")
                when(it){
                    is ResultState.Success -> {
                        it.data == actualValue.data
                    }
                    else -> false
                }
            }.dispose()
        }

        verify(localDataSource, atLeastOnce()).deleteFavoriteMovie(movieId)
    }

    @Test
    fun `localDataSource-deleteFavoriteMovie = should return Error`(){
        val expectedValue = ResultState.Error("Delete Failed")
        val movieId = 123

        whenever(localDataSource.deleteFavoriteMovie(movieId))
            .thenReturn(Single.just(expectedValue))

        localDataSource.deleteFavoriteMovie(movieId).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val actualValue = ResultState.Error("Delete Failed")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }

        verify(localDataSource, atLeastOnce()).deleteFavoriteMovie(movieId)
    }

    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
    }
}