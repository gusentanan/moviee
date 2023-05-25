package com.bagusmerta.moviee.main

import com.bagusmerta.core.data.source.remote.movieeResponse.MovieeResponse
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.utils.testHelper.TrampolineRxSchedulers
import com.bagusmerta.core.utils.testHelper.getSingleDummyMoviee
import com.bagusmerta.core.utils.testHelper.load
import com.bagusmerta.core.utils.testHelper.mapResponseToDomain
import com.bagusmerta.moviee.presentation.detail.DetailViewModel
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


class DetailViewModelTest {
    private val useCase: MovieeUseCase = mock()
    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        detailViewModel = DetailViewModel(useCase)

        TrampolineRxSchedulers.start()
    }

    @Test
    fun `when check favorite movie make sure it executed at least once`(){
        val expectedValue = getSingleDummyMoviee()
        val id = 123
        whenever(useCase.checkFavoriteMovies(id))
            .thenReturn(Maybe.just(getSingleDummyMoviee()))

        detailViewModel.checkFavoriteMovies(id)
        useCase.checkFavoriteMovies(id).test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = mapResponseToDomain(json)

                it.title == actualValue[1].title
            }.dispose()
        }
        verify(useCase, atLeastOnce()).checkFavoriteMovies(id)
    }

    @Test
    fun `when change favorite state make sure it executed at least once`(){
        val movie = getSingleDummyMoviee()
        val state = true
        whenever(useCase.setFavoriteMovies(movie, state))
            .thenReturn(Single.just(Unit))

        detailViewModel.setFavoriteMovies(movie, state)
        useCase.setFavoriteMovies(movie, state).test().apply {
            assertComplete()
            assertNoErrors()
            assertTerminated()
        }
        verify(useCase, atLeastOnce()).setFavoriteMovies(movie, state)
    }

    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
    }
}