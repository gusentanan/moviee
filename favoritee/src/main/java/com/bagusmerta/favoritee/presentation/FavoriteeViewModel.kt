package com.bagusmerta.favoritee.presentation

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.domain.usecase.MovieeUseCase

class FavoriteeViewModel(useCase: MovieeUseCase): ViewModel() {

    val favoriteMovieList = LiveDataReactiveStreams.fromPublisher(useCase.getAllFavoriteMovies(true))
}