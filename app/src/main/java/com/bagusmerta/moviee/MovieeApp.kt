package com.bagusmerta.moviee

import android.app.Application
import com.bagusmerta.core.di.networkModule
import com.bagusmerta.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieeApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MovieeApp)
            modules(
                /**  put your app module here */
                networkModule,
                repositoryModule
            )
        }
    }
}