package com.bagusmerta.moviee.main

import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeResponse
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.utils.testHelper.TrampolineRxSchedulers
import com.bagusmerta.core.utils.testHelper.getDummyMoviee
import com.bagusmerta.core.utils.testHelper.load
import com.bagusmerta.core.utils.testHelper.mapResponseToDomain
import com.bagusmerta.moviee.presentation.search.SearchViewModel
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchViewModelTest {
    private var useCase: MovieeUseCase = mock()
    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        searchViewModel = SearchViewModel(useCase)

        TrampolineRxSchedulers.start()
    }

    @Test
    fun `when success search movies should return list of movies`(){
        val expectedValue = Resource.Success(getDummyMoviee())
        val query = "title"
        whenever(useCase.searchMovies(query))
            .thenReturn(Single.just(expectedValue))

        searchViewModel.searchMovies(query)

        val testCase = useCase.searchMovies(query).test()
        testCase.awaitTerminalEvent()
        testCase.apply {
            assertNoErrors()
            assertComplete()
            assertValueCount(1)
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = Resource.Success(mapResponseToDomain(json))
                when(it){
                    is Resource.Success -> it.data.size == actualValue.data.size
                    else -> false
                }
            }
        }.dispose()

        verify(useCase, atLeastOnce()).searchMovies(query)
    }

    @Test
    fun `when success search movies but empty should return empty state`(){
        val query = "title"
        whenever(useCase.searchMovies(query))
            .thenReturn(Single.just(Resource.Empty))

        searchViewModel.searchMovies(query)

        val testCase = useCase.searchMovies(query).test()
        testCase.awaitTerminalEvent()
        testCase.apply {
            assertNoErrors()
            assertComplete()
            assertValue {
                it == Resource.Empty
            }
        }.dispose()

        verify(useCase, atLeastOnce()).searchMovies(query)
    }

    @Test
    fun `when failed search movies should return error message`(){
        val errorMessage = "Opps Something Happen!"
        val expectedValue = Resource.Error(errorMessage)
        val query = "title"
        whenever(useCase.searchMovies(query))
            .thenReturn(Single.just(expectedValue))

        searchViewModel.searchMovies(query)

        val testCase = useCase.searchMovies(query).test()
        testCase.awaitTerminalEvent()
        testCase.apply {
            assertValue {
                when(it){
                    is Resource.Error -> it.errorMessage == errorMessage
                    else -> false
                }
            }
        }.dispose()

        verify(useCase, atLeastOnce()).searchMovies(query)
    }

    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
    }

}