package com.bagusmerta.moviee

import android.app.Application
import com.bagusmerta.core.di.databaseModule
import com.bagusmerta.core.di.networkModule
import com.bagusmerta.core.di.repositoryModule
import com.bagusmerta.moviee.di.useCaseModule
import com.bagusmerta.moviee.di.viewModelModule
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
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}