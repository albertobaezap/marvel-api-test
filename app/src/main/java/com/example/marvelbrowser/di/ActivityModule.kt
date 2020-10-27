package com.example.marvelbrowser.di

import androidx.lifecycle.ViewModelProvider
import com.example.marvelbrowser.data.CharacterStoreController
import com.example.marvelbrowser.model.Character
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val activityModule = Kodein.Module("ActivityModule") {
    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(kodein.direct)
    }
}