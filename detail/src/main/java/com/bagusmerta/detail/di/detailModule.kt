package com.bagusmerta.detail.di

import com.bagusmerta.detail.presentation.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { DetailViewModel(get()) }
}