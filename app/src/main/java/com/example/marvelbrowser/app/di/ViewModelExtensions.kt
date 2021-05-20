package com.example.marvelbrowser.app.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.marvelbrowser.app.base.BasePresenter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

/**
 * Lazy method to provide the view model.
 */
inline fun <reified VM: ViewModel, T> T.mainViewModel(): Lazy<VM> where T: KodeinAware,T: Fragment =
    viewModels(factoryProducer = { direct.instance()})

/**
 * Binds a ViewModel to a Kotlin module.
 */
inline fun <reified VM : ViewModel> Kodein.Builder.bindViewModel(overrides: Boolean? = null): Kodein.Builder.TypeBinder<VM> =
    bind<VM>(VM::class.java.simpleName, overrides)