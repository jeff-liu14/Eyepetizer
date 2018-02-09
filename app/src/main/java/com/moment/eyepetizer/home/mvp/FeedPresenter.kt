package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */
class FeedPresenter(var feedView: FeedContract.FeedView) : FeedContract.FeedPresenter {

    init {
        feedView.setPresenter(this)
    }

    override fun start() = Unit

    override fun feed(date: Long): Disposable = GetDataList.feed(date, object : CallBack<Result> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable) = feedView.onFeedFail(e)

        override fun onNext(t: Result) = feedView.onFeedSucc(t)

    })

}