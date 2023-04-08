package com.bagusmerta.core.di

import androidx.room.Room
import com.bagusmerta.core.BuildConfig
import com.bagusmerta.core.data.MovieeRepository
import com.bagusmerta.core.data.MovieeRepositoryImpl
import com.bagusmerta.core.data.source.local.LocalDataSource
import com.bagusmerta.core.data.source.local.db.MovieeDatabase
import com.bagusmerta.core.data.source.remote.ApiConfig.MovieeService
import com.bagusmerta.core.data.source.remote.RemoteDataSource
import com.bagusmerta.core.utils.Constants.API_KEY
import com.bagusmerta.core.utils.Constants.BASE_URL
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieeDatabase>().movieeDao() }
    single {
        val factory = SupportFactory(
            SQLiteDatabase.getBytes("moviee".toCharArray())
        )
        Room.databaseBuilder(
            androidContext(),
            MovieeDatabase::class.java, "Moviee.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
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


private fun retrofitBuilder(okHttpClient: OkHttpClient): MovieeService {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
        .create(MovieeService::class.java)
}

private fun okHttpClientBuilder(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(serviceHttpClient())
        .addInterceptor(loggingInterceptor())
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .certificatePinner(certificatePinningBuilder())
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

private fun certificatePinningBuilder(): CertificatePinner {
    return CertificatePinner.Builder()
        .add(BuildConfig.BASE_API, "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
        .build()
}