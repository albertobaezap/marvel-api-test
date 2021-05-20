package com.example.marvelbrowser

import android.app.Application
import android.content.Context
import com.example.marvelbrowser.app.di.networkModule
import com.example.marvelbrowser.data.CharacterStoreController
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import timber.log.Timber
import kotlin.properties.Delegates

private var appInstance: Application by Delegates.notNull()

/**
 * Main App module to configure dependencies.
 */
class MarvelApp: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(networkModule)
    }

    override fun onCreate() {
        appInstance = this
        super.onCreate()

        //Plant timber tree for logging purposes
        Timber.plant(Timber.DebugTree())
    }
}