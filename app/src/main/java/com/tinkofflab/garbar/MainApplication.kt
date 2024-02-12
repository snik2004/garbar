package com.tinkofflab.garbar

import android.app.Application
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    appModule,
                )
            )
        }
        Timber.plant(Timber.DebugTree())
        RxJavaPlugins.setErrorHandler { throwable ->
            Timber.e( "RxJavaPlugins $throwable")
        }
        Timber.d("INIT APPLICATION")
    }

}