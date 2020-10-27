package com.example.marvelbrowser

import android.app.Application
import android.content.Context
import com.example.marvelbrowser.data.CharacterStoreController
import com.example.marvelbrowser.di.activityModule
import com.example.marvelbrowser.di.networkModule
import com.example.marvelbrowser.di.viewModelModule
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
        import(appModule)
        import(activityModule)
        import(networkModule)
        import(viewModelModule)
    }

    override fun onCreate() {
        appInstance = this
        super.onCreate()

        //Plant timber tree for logging purposes
        Timber.plant(Timber.DebugTree())
    }
}

val appModule = Kodein.Module("AppModule") {
    bind<Context>() with singleton { appInstance }
    bind<CharacterStoreController>() with singleton {
        CharacterStoreController(instance())
    }
}