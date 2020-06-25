package com.novachevskyi.pokemon_mvi

import android.app.Application
import com.novachevskyi.pokemon_mvi.di.appModule
import com.novachevskyi.pokemon_mvi.di.remoteDataSourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            fileProperties()
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(appModule)
            modules(remoteDataSourceModule)
        }
    }
}
