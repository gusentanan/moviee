/*
 * Designed and developed by 2023 gusentanan (Bagus Merta)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bagusmerta.core.di

import androidx.room.Room
import com.bagusmerta.core.data.MovieeRepository
import com.bagusmerta.core.data.MovieeRepositoryImpl
import com.bagusmerta.core.data.source.local.LocalDataSource
import com.bagusmerta.core.data.source.local.db.MovieeDatabase
import com.bagusmerta.core.data.source.remote.RemoteDataSource
import com.bagusmerta.core.data.source.remote.apiConfig.MovieeService
import com.bagusmerta.core.utils.Constants.API_KEY
import com.bagusmerta.core.utils.Constants.BASE_URL
import com.bagusmerta.core_logic.BuildConfig
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
import retrofit2.converter.moshi.MoshiConverterFactory
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
        .addConverterFactory(MoshiConverterFactory.create())
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
//        .certificatePinner(certificatePinningBuilder())
        .build()
}

private fun serviceHttpClient(): Interceptor {
    return Interceptor { chain ->
        val request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val modifiedRequest = request.newBuilder()
            .url(url)
            .addHeader("Accept", "application/json")
            .build()

        chain.proceed(modifiedRequest)
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
