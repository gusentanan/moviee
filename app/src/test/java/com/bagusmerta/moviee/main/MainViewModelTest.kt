package com.bagusmerta.moviee.main

import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeResponse
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.utils.testHelper.TrampolineRxSchedulers
import com.bagusmerta.core.utils.testHelper.getDummyMoviee
import com.bagusmerta.core.utils.testHelper.load
import com.bagusmerta.core.utils.testHelper.mapResponseToDomain
import com.bagusmerta.moviee.presentation.main.MainViewModel
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
    }

    @Test
    fun `when success fetch data should return list of movie`(){
        val expectedValue = Resource.Success(getDummyMoviee())

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

    @Test
    fun `when success fetch data but empty should return empty state`(){
        whenever(useCase.getAllMovies())
            .thenReturn(Flowable.just(Resource.Empty))

        mainViewModel.getAllMovies()

        val testCase = useCase.getAllMovies().test()
        testCase.awaitTerminalEvent()
        testCase.apply {
            assertNoErrors()
            assertComplete()
            assertValue{
                it == Resource.Empty
            }
        }.dispose()
        verify(useCase, atLeastOnce()).getAllMovies()
    }

    @Test
    fun `when failed fetch data should return error message`(){
        val errorMessage = "Oops something happen!"
        val expectedValue = Resource.Error(errorMessage)

        whenever(useCase.getAllMovies())
            .thenReturn(Flowable.just(expectedValue))

        mainViewModel.getAllMovies()

        val testCase = useCase.getAllMovies().test()
        testCase.awaitTerminalEvent()
        testCase.apply {
            assertNoErrors()
            assertComplete()
            assertValue {
                when(it){
                    is Resource.Error -> it.errorMessage == errorMessage
                    else -> false
                }
            }
        }.dispose()
        verify(useCase, atLeastOnce()).getAllMovies()
    }

    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
    }

}