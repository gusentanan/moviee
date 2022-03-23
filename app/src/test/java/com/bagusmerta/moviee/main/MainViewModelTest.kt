package com.bagusmerta.moviee.main

import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeResponse
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.utils.DataMapper
import com.bagusmerta.moviee.presentation.main.MainViewModel
import com.bagusmerta.moviee.main.testHelper.InstantTaskExecutor
import com.bagusmerta.moviee.main.testHelper.TrampolineRxSchedulers
import com.bagusmerta.moviee.main.testHelper.getDummyResponse
import com.bagusmerta.moviee.main.testHelper.load
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MainViewModelTest {
    private val useCase: MovieeUseCase = mock()
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel(useCase)

        TrampolineRxSchedulers.start()
        InstantTaskExecutor.start()
    }

    @Test
    fun `when success fetch data should return list of movie`(){
        val expectedValue = Resource.Success(getDummyResponse())

        whenever(useCase.getAllMovies())
            .thenReturn(Flowable.just(expectedValue))

        mainViewModel.getAllMovies()

        val testCase = useCase.getAllMovies().test()
        testCase.awaitTerminalEvent()
        testCase.apply {
            assertNoErrors()
            assertComplete()
            assertValueCount(1)
            assertValue{
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = Resource.Success(mapResponseToDomain(json))
                when(it){
                    is Resource.Success -> it.data.size == actualValue.data.size
                    else -> false
                }
            }.dispose()
        }
        verify(useCase, atLeastOnce()).getAllMovies()
    }

    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
        InstantTaskExecutor.end()
    }

    private fun mapResponseToDomain(data: MovieeResponse): List<Moviee> {
        return DataMapper.mapListMovieeResponseToEntity(data.movieeResponse).let {
            DataMapper.mapListMovieeEntityToDomain(it)
        }
    }
}