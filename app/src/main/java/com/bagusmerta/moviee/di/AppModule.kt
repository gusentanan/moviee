package com.bagusmerta.moviee.di


import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.domain.usecase.MovieeUseCaseImpl
import com.bagusmerta.moviee.presentation.all.AllMovieViewModel
import com.bagusmerta.moviee.presentation.detail.DetailViewModel
import com.bagusmerta.moviee.presentation.main.MainViewModel
import com.bagusmerta.moviee.presentation.search.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieeUseCase> { MovieeUseCaseImpl(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), androidApplication()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { AllMovieViewModel(get()) }
}
