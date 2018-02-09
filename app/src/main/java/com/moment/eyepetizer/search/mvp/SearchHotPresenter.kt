package com.moment.eyepetizer.search.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/8.
 */

class SearchHotPresenter(var searchView: SearchContract.SearchHotView) : SearchContract.SearchHotPresenter {

    init {
        searchView.setPresenter(this)
    }

    override fun start() = Unit

    override fun hot(): Disposable = GetDataList.hot(object : CallBack<List<String>> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable) = Unit

        override fun onNext(t: List<String>) = searchView.onHotSucc(t)

    })

}