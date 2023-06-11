package com.bagusmerta.feature.allmovie.di

import com.bagusmerta.feature.allmovie.presentation.AllMovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val allMovieModule = module {
    viewModel { AllMovieViewModel(get()) }
}