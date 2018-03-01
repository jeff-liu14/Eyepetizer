package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */

class DiscoveryPresenter(var discoveryView: DiscoveryContract.DiscoveryView) : DiscoveryContract.DiscoveryPresenter {

    init {
        discoveryView.setPresenter(this)
    }

    override fun start() = Unit


    override fun discovery(): Disposable = GetDataList.discovery(object : CallBack<Result> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable?) = discoveryView.onDiscoveryFail(e)

        override fun onNext(t: Result) = discoveryView.onDiscoverySucc(t)

    })

}