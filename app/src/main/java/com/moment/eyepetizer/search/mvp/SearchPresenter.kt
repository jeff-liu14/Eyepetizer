package com.moment.eyepetizer.search.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/8.
 */

class SearchPresenter(var searchView: SearchContract.SearchView) : SearchContract.SearchPresenter {

    init {
        searchView.setPresenter(this)
    }

    override fun start() = Unit

    override fun search(start: Int, num: Int, search: String): Disposable = GetDataList.search(start, num, search, object : CallBack<Result> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable) = Unit

        override fun onNext(t: Result) = searchView.onSearchSucc(t)

    })

}