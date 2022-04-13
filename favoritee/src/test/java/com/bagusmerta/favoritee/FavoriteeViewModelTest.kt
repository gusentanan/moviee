package com.bagusmerta.favoritee


import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.utils.testHelper.TrampolineRxSchedulers
import com.bagusmerta.core.utils.testHelper.getDummyResponse
import com.bagusmerta.favoritee.presentation.FavoriteeViewModel
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class FavoriteeViewModelTest {
    private val useCase: MovieeUseCase = mock()
    private lateinit var favoriteeViewModel: FavoriteeViewModel

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        favoriteeViewModel = FavoriteeViewModel(useCase)

        TrampolineRxSchedulers.start()
    }

    /* TODO : Complete unit test on favoritee module **/

    @Test
    fun `when success fetch favorite movie should return list of movie`(){
        val expectedValue = getDummyResponse()

        whenever(useCase.getAllFavoriteMovies(true))
            .thenReturn(Flowable.just(expectedValue))

        favoriteeViewModel.getFavoriteMovies(true)
        verify(useCase, atLeastOnce()).getAllFavoriteMovies(true)
    }

    @Test
    fun `when success fetch favorite movie but empty should return empty state`(){
        val expectedValue = emptyList<Moviee>()

        whenever(useCase.getAllFavoriteMovies(true))
            .thenReturn(Flowable.just(expectedValue))

        favoriteeViewModel.getFavoriteMovies(true)
        verify(useCase, atLeastOnce()).getAllFavoriteMovies(true)
    }

    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
    }
}