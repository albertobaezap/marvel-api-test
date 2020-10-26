package com.example.marvelbrowser.di

import com.example.marvelbrowser.ui.main.MainViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val viewModelModule = Kodein.Module("ViewModel") {
    bindViewModel<MainViewModel>() with provider {
        MainViewModel(instance())
    }
}