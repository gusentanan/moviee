/*
 * Designed and developed by 2023 gusentanan (Bagus Merta)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bagusmerta.core

import com.bagusmerta.core.data.source.remote.RemoteDataSource
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieCastResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeDetailResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeResponse
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeSearchResponse
import com.bagusmerta.core.utils.testHelper.*
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


class RemoteDataSourceTest {
    private val remoteDataSource: RemoteDataSource = mock()

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        TrampolineRxSchedulers.start()
    }

    @Test
    fun `remoteDataSource-getAllMovies() = should return list of Movies`(){
        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())

        whenever(remoteDataSource.getAllMovies())
            .thenReturn(Flowable.just(expectedValue))

        remoteDataSource.getAllMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = ResultState.Success(json)
                when(it){
                    is ResultState.Success -> {
                        it.data.size == actualValue.data.movieeResponse.size
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getAllMovies()
    }

    @Test
    fun `remoteDataSource-getAllMovies() = should return Error`(){
        val expectedValue = ResultState.Error("Opps something wrong happen!")

        whenever(remoteDataSource.getAllMovies())
            .thenReturn(Flowable.just(expectedValue))

        remoteDataSource.getAllMovies().test().apply {
            assertComplete()
            assertValue {
                val actualValue = ResultState.Error("Opps something wrong happen!")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getAllMovies()
    }


    @Test
    fun `remoteDataSource-searchMovies() = should return list of Movies `(){
        val expectedValue = ResultState.Success(getDummySearchResponse())

        val exTitle = "Suicide Squad"
        whenever(remoteDataSource.searchMovies(exTitle))
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.searchMovies(exTitle).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeSearchResponse::class.java, "response/movie_response.json")
                val actualValue = json.movieeItemSearch
                when(it){
                    is ResultState.Success -> {
                        it.data[0].movieeTitle == actualValue[0].movieeTitle
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).searchMovies(exTitle)
    }

    @Test
    fun `remoteDataSource-searchMovies() = should return Error`(){
        val expectedValue = ResultState.Error("Opps something wrong happen!")
        val exTitle = "title"

        whenever(remoteDataSource.searchMovies(exTitle))
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.searchMovies(exTitle).test().apply {
            assertComplete()
            assertValue {
                val actualValue = ResultState.Error("Opps something wrong happen!")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).searchMovies(exTitle)
    }

    @Test
    fun `remoteDataSource-searchMovies() = should return Empty`(){
        val expectedValue = ResultState.Empty
        val exTitle = "No title"
        whenever(remoteDataSource.searchMovies(exTitle))
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.searchMovies(exTitle).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val actualValue = ResultState.Empty
                when(it){
                    is ResultState.Empty -> it == actualValue
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).searchMovies(exTitle)
    }

    @Test
    fun `remoteDataSource-getTopRatedMovies() = should return list of Movies`(){
        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())

        whenever(remoteDataSource.getTopRatedMovies())
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getTopRatedMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = ResultState.Success(json)
                when(it){
                    is ResultState.Success -> {
                        it.data.size == actualValue.data.movieeResponse.size
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getTopRatedMovies()
    }

    @Test
    fun `remoteDataSource-getTopRatedMovies() = should return Error`(){
        val expectedValue = ResultState.Error("Opps something wrong happen!")

        whenever(remoteDataSource.getTopRatedMovies())
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getTopRatedMovies().test().apply {
            assertComplete()
            assertValue {
                val actualValue = ResultState.Error("Opps something wrong happen!")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getTopRatedMovies()
    }

    @Test
    fun `remoteDataSource-getUpcomingMovies() = should return list of Movies`(){
        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())

        whenever(remoteDataSource.getUpcomingMovies())
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getUpcomingMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = ResultState.Success(json)
                when(it){
                    is ResultState.Success -> {
                        it.data.size == actualValue.data.movieeResponse.size
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getUpcomingMovies()
    }

    @Test
    fun `remoteDataSource-getUpcomingMovies() = should return Error`(){
        val expectedValue = ResultState.Error("Opps something wrong happen!")

        whenever(remoteDataSource.getUpcomingMovies())
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getUpcomingMovies().test().apply {
            assertComplete()
            assertValue {
                val actualValue = ResultState.Error("Opps something wrong happen!")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getUpcomingMovies()
    }

    @Test
    fun `remoteDataSource-getNowPLayingMovies() = should return list of Movies`(){
        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())

        whenever(remoteDataSource.getNowPlayingMovies())
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getNowPlayingMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = ResultState.Success(json)
                when(it){
                    is ResultState.Success -> {
                        it.data.size == actualValue.data.movieeResponse.size
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getNowPlayingMovies()
    }

    @Test
    fun `remoteDataSource-getNowPlayingMovies() = should return Error`(){
        val expectedValue = ResultState.Error("Opps something wrong happen!")

        whenever(remoteDataSource.getNowPlayingMovies())
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getNowPlayingMovies().test().apply {
            assertComplete()
            assertValue {
                val actualValue = ResultState.Error("Opps something wrong happen!")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getNowPlayingMovies()
    }

    @Test
    fun `remoteDataSource-getPopularMovies() = should return list of Movies`(){
        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())

        whenever(remoteDataSource.getPopularMovies())
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getPopularMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = ResultState.Success(json)
                when(it){
                    is ResultState.Success -> {
                        it.data.size == actualValue.data.movieeResponse.size
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getPopularMovies()
    }

    @Test
    fun `remoteDataSource-getPopularMovies() = should return Error`(){
        val expectedValue = ResultState.Error("Opps something wrong happen!")

        whenever(remoteDataSource.getPopularMovies())
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getPopularMovies().test().apply {
            assertComplete()
            assertValue {
                val actualValue = ResultState.Error("Opps something wrong happen!")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getPopularMovies()
    }

    @Test
    fun `remoteDataSource-getSimilarMovies() = should return list of Movies`(){
        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())
        val movieId = 21

        whenever(remoteDataSource.getSimilarMovies(movieId))
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getSimilarMovies(movieId).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = ResultState.Success(json)
                when(it){
                    is ResultState.Success -> {
                        it.data.size == actualValue.data.movieeResponse.size
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getSimilarMovies(movieId)
    }

    @Test
    fun `remoteDataSource-getSimilarMovies() = should return Error`(){
        val expectedValue = ResultState.Error("Opps something wrong happen!")
        val movieId = 21
        whenever(remoteDataSource.getSimilarMovies(movieId))
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getSimilarMovies(movieId).test().apply {
            assertComplete()
            assertValue {
                val actualValue = ResultState.Error("Opps something wrong happen!")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getSimilarMovies(movieId)
    }

    @Test
    fun `remoteDataSource-getSimilarMovies() = should return Empty`(){
        val expectedValue = ResultState.Empty
        val movieId = 21
        whenever(remoteDataSource.getSimilarMovies(movieId))
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getSimilarMovies(movieId).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val actualValue = ResultState.Empty
                when(it){
                    is ResultState.Empty -> it == actualValue
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getSimilarMovies(movieId)
    }

    @Test
    fun `remoteDataSource-getMovieCast() = should return list of Cast`(){
        val expectedValue = ResultState.Success(getDummyCastResponse())
        val movieId = 21

        whenever(remoteDataSource.getMovieCast(movieId))
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getMovieCast(movieId).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieCastResponse::class.java, "response/cast_response.json")
                val actualValue = ResultState.Success(json)
                when(it){
                    is ResultState.Success -> {
                        it.data.size == actualValue.data.movieCast.size
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getMovieCast(movieId)
    }

    @Test
    fun `remoteDataSource-getMovieCast() = should return Error`(){
        val expectedValue = ResultState.Error("Opps something wrong happen!")
        val movieId = 21
        whenever(remoteDataSource.getMovieCast(movieId))
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getMovieCast(movieId).test().apply {
            assertComplete()
            assertValue {
                val actualValue = ResultState.Error("Opps something wrong happen!")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getMovieCast(movieId)
    }

    @Test
    fun `remoteDataSource-getDetailMovies() = should return detail movies information`(){
        val expectedValue = ResultState.Success(getSingleDummyMoviee())
        val movieId = 21

        whenever(remoteDataSource.getDetailMovies(movieId))
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getDetailMovies(movieId).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val actualValue = load(MovieeDetailResponse::class.java, "response/movie_detail_response.json")

                when(it){
                    is ResultState.Success -> {
                        it.data.movieeTitle == actualValue.movieeTitle
                    }
                    else -> false
                }
            }.dispose()
        }

        verify(remoteDataSource, atLeastOnce()).getDetailMovies(movieId)
    }

    @Test
    fun `remoteDataSource-getDetailMovies() = should return Error`(){
        val expectedValue = ResultState.Error("Opps something wrong happen!")
        val movieId = 21
        whenever(remoteDataSource.getDetailMovies(movieId))
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getDetailMovies(movieId).test().apply {
            assertComplete()
            assertValue {
                val actualValue = ResultState.Error("Opps something wrong happen!")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }
        verify(remoteDataSource, atLeastOnce()).getDetailMovies(movieId)
    }

    @Test
    fun `remoteDataSource-getTrendingMovies() = should return list of Movie`(){
        val expectedValue = ResultState.Success(getDummyMovieeItemResponse())

        whenever(remoteDataSource.getTrendingMovies())
             .thenReturn(Single.just(expectedValue))

        remoteDataSource.getTrendingMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = ResultState.Success(json)

                when(it){
                    is ResultState.Success -> {
                        it.data.size == actualValue.data.movieeResponse.size
                    }
                    else -> false
                }
            }.dispose()
        }

        verify(remoteDataSource, atLeastOnce()).getTrendingMovies()
    }

    @Test
    fun `remoteDataSource-getTrendingMovies() = should return Empty`(){
        val expectedValue = ResultState.Empty

        whenever(remoteDataSource.getTrendingMovies())
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getTrendingMovies().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val actualValue = ResultState.Empty
                when(it){
                    ResultState.Empty -> {
                        it == actualValue
                    }
                    else -> false
                }
            }.dispose()
        }

        verify(remoteDataSource, atLeastOnce()).getTrendingMovies()
    }

    @Test
    fun `remoteDataSource-getTrendingMovies = should return Error`(){
        val expectedValue = ResultState.Error("Oops something wrong happen")

        whenever(remoteDataSource.getTrendingMovies())
            .thenReturn(Single.just(expectedValue))

        remoteDataSource.getTrendingMovies().test().apply {
            assertComplete()
            assertValue {
                val actualValue = ResultState.Error("Oops something wrong happen")
                when(it){
                    is ResultState.Error -> {
                        it.errorMessage == actualValue.errorMessage
                    }
                    else -> false
                }
            }.dispose()
        }

        verify(remoteDataSource, atLeastOnce()).getTrendingMovies()
    }


    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
    }
}
