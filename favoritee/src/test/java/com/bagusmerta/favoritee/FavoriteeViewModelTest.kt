package com.bagusmerta.favoritee


import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeResponse
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.utils.testHelper.TrampolineRxSchedulers
import com.bagusmerta.core.utils.testHelper.getDummyResponse
import com.bagusmerta.core.utils.testHelper.load
import com.bagusmerta.core.utils.testHelper.mapResponseToDomain
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

    @Test
    fun `when success fetch favorite movie should return list of movie`(){
        val expectedValue = getDummyResponse()

        whenever(useCase.getAllFavoriteMovies(true))
            .thenReturn(Flowable.just(expectedValue))

        val testCase = useCase.getAllFavoriteMovies(true).test()
        testCase.awaitTerminalEvent()
        testCase.apply {
            assertNoErrors()
            assertComplete()
            assertValue {
                val json = load(MovieeResponse::class.java, "response/movie_response.json")
                val actualValue = mapResponseToDomain(json)
                it.size == actualValue.size
            }
        }.dispose()

        favoriteeViewModel.getFavoriteMovies(true)
        verify(useCase, atLeastOnce()).getAllFavoriteMovies(true)
    }

    @After
    fun tearDown(){
        TrampolineRxSchedulers.end()
    }
}