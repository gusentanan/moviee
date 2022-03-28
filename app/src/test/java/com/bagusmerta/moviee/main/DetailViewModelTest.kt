package com.bagusmerta.moviee.main

import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.moviee.main.testHelper.InstantTaskExecutor
import com.bagusmerta.moviee.main.testHelper.TrampolineRxSchedulers
import com.bagusmerta.moviee.main.testHelper.getSingleMovieResponse
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

/* This test case is Not Good due to my lack of understanding in making a good code.
   for some, good code can mean code that comes with a full suite of unit tests

   maybe im gonna do refactoring but for now i will let it like this.
*/

class DetailViewModelTest {
    private val useCase: MovieeUseCase = mock()
    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        detailViewModel = DetailViewModel(useCase)

        TrampolineRxSchedulers.start()
        InstantTaskExecutor.start()
    }

    @Test
    fun `when check favorite movie make sure it executed at least once`(){
        val id = 123
        whenever(useCase.checkFavoriteMovies(id))
            .thenReturn(Maybe.just(getSingleMovieResponse()))

        detailViewModel.checkFavoriteMovies(id)
        verify(useCase, atLeastOnce()).checkFavoriteMovies(id)
    }

    @Test
    fun `when change favorite state make sure it executed at least once`(){
        val movie = getSingleMovieResponse()
        whenever(useCase.setFavoriteMovies(movie, movie.isFavorite!!))
            .thenReturn(Single.just(Unit))

        detailViewModel.setFavoriteMovies(movie, movie.isFavorite!!)
        verify(useCase, atLeastOnce()).setFavoriteMovies(movie, movie.isFavorite!!)
    }

    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
        InstantTaskExecutor.end()
    }
}