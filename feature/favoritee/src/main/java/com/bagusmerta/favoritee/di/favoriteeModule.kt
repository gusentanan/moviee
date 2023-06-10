package com.bagusmerta.feature.favoritee.di

import com.bagusmerta.feature.favoritee.presentation.FavoriteeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteeModule = module {
    viewModel { FavoriteeViewModel(get()) }
}