package com.bagusmerta.core.data

import android.annotation.SuppressLint
import com.bagusmerta.core.data.source.remote.MovieeResponse.MovieeItemResponse
import com.bagusmerta.core.data.source.remote.RemoteDataSource
import com.bagusmerta.core.domain.model.MovieeEntity
import com.bagusmerta.core.utils.DataMapper
import com.bagusmerta.core.utils.ResultState
import io.reactivex.Flowable


interface MovieeRepository {
    fun getAllMovies(): Flowable<Resource<List<MovieeEntity>>>
}

class MovieeRepositoryImpl(private val remoteDataSource: RemoteDataSource): MovieeRepository {
    @SuppressLint("CheckResult")
    override fun getAllMovies(): Flowable<Resource<List<MovieeEntity>>> =
       object : NetworkBoundResource<List<MovieeEntity>, List<MovieeItemResponse>>() {
           override fun loadFromDB(): Flowable<List<MovieeEntity>> {
               TODO("Not yet implemented")
           }

           override fun shouldFetch(data: List<MovieeEntity>?): Boolean {
               return true
           }

           override fun createCall(): Flowable<ResultState<List<MovieeItemResponse>>> {
               return remoteDataSource.getAllMovies()
           }

           override fun saveCallResult(data: List<MovieeItemResponse>) {
               val movieeList = DataMapper.mapMovieeResponseToDomain(data)
           }

       }.asFlowable()

}