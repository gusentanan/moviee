package com.bagusmerta.core.di

import androidx.room.Room
import com.bagusmerta.core.data.MovieeRepository
import com.bagusmerta.core.data.MovieeRepositoryImpl
import com.bagusmerta.core.data.source.local.LocalDataSource
import com.bagusmerta.core.data.source.local.db.MovieeDatabase
import com.bagusmerta.core.data.source.remote.RemoteDataSource
import com.bagusmerta.core.utils.Constants.API_KEY
import com.bagusmerta.core.utils.Constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    single { get<MovieeDatabase>().movieeDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieeDatabase::class.java, "moviee.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single { okHttpClientBuilder() }
    single { retrofitBuilder(get()) }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }

    single<MovieeRepository> { MovieeRepositoryImpl(get(), get()) }
}



private fun retrofitBuilder(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun okHttpClientBuilder(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(serviceHttpClient())
        .addInterceptor(loggingInterceptor())
        .connectTimeout(120, TimeUnit.MILLISECONDS)
        .readTimeout(120, TimeUnit.MILLISECONDS)
        .build()
}

private fun serviceHttpClient(): Interceptor {
    return Interceptor { chain ->  
        var request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        request = request.newBuilder().url(url).addHeader("Accept", "application/json")
            .build()
        return@Interceptor chain.proceed(request)
    }
}

private fun loggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor =
        HttpLoggingInterceptor().let {
            if (BuildConfig.DEBUG) it.setLevel(HttpLoggingInterceptor.Level.BODY)
            else it.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    return loggingInterceptor
}