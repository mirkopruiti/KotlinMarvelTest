package com.mirkopruiti.kotlinmarveltest.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mirkopruiti.kotlinmarveltest.remote.api.ApiService
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

abstract class BaseViewModel<T>(val apiService: ApiService) : ViewModel() {

    private val currentCalls = CompositeDisposable()
    val liveData = MutableLiveData<ArrayList<T>>()
    var offset = 0
    val count = 20
    var total = Int.MAX_VALUE
    var loading = false
    var isFirstRequest = true

    protected fun callApi(call: Observable<MarvelResponse<T>>) {
        currentCalls.add(call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<MarvelResponse<T>>() {
                    override fun onComplete() {
                    }

                    override fun onNext(response: MarvelResponse<T>) {
                        val responseData = response.data

                        total = responseData.total

                        offset += count
                        loading = false
                        isFirstRequest = false

                        if (liveData.value == null) {
                            liveData.value = responseData.results
                        } else {
                            liveData.value!!.addAll(responseData.results)
                            liveData.postValue(liveData.value)
                        }
                    }

                    override fun onError(e: Throwable) {
                        liveData.postValue(null)
                        loading = false
                    }
                })
        )
        loading = true
    }

    fun reset() {
        offset = 0
        total = Int.MAX_VALUE
        liveData.value = arrayListOf()
        currentCalls.clear()
        loading = false
        isFirstRequest = true
    }

    override fun onCleared() {
        super.onCleared()
        currentCalls.dispose()
    }
}