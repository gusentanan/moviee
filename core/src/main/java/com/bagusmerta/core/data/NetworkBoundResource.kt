package com.bagusmerta.core.data

import android.annotation.SuppressLint
import com.bagusmerta.utility.ResultState
import com.bagusmerta.utility.flowableTransformerComputation
import com.bagusmerta.utility.flowableTransformerIo
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

@SuppressLint("CheckResult")
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = PublishSubject.create<Resource<ResultType>>()
    private val mCompositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        val dbSource = loadFromDB()
        val db = dbSource
            .compose(flowableTransformerIo())
            .subscribe { value ->
                dbSource.unsubscribeOn(Schedulers.io())
                if (shouldFetch(value)) {
                    fetchFromNetwork()
                } else {
                    result.onNext(Resource.Success(value))
                }
            }
        mCompositeDisposable.add(db)
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flowable<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): Flowable<ResultState<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork() {

        val apiResponse = createCall()

        val response = apiResponse
            .compose(flowableTransformerIo())
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe { response ->
                when (response) {
                    is ResultState.Success -> {
                        saveCallResult(response.data)
                        val dbSource = loadFromDB()
                        dbSource.compose(flowableTransformerComputation())
                            .subscribe {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Success(it))
                            }
                    }
                    is ResultState.Empty -> {
                        val dbSource = loadFromDB()
                        dbSource.compose(flowableTransformerComputation())
                            .subscribe {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Success(it))
                            }
                    }
                    is ResultState.Error -> {
                        onFetchFailed()
                        result.onNext(Resource.Error(response.errorMessage))
                    }
                }
            }
        mCompositeDisposable.add(response)
    }

    fun asFlowable(): Flowable<Resource<ResultType>> =
        result.toFlowable(BackpressureStrategy.BUFFER)
}