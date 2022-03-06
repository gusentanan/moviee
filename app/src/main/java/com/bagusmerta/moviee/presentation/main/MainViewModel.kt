package com.bagusmerta.moviee.presentation.main

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.domain.usecase.MovieeUseCase


class MainViewModel(useCase: MovieeUseCase): ViewModel() {

    val movieeList = LiveDataReactiveStreams.fromPublisher(useCase.getAllMovies())
}