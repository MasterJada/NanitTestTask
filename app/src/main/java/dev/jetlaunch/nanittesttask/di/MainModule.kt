package dev.jetlaunch.nanittesttask.di

import dev.jetlaunch.nanittesttask.core.BabyDataProcessor
import dev.jetlaunch.nanittesttask.viewmodels.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val  MainModule = module {
    single { BabyDataProcessor(get()) }
    viewModel { MainViewModel(get()) }
}