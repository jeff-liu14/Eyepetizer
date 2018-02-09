package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */

interface FeedContract {
    interface FeedPresenter : BasePresenter {
        fun feed(date: Long): Disposable
    }

    interface FeedView : BaseView<FeedPresenter> {
        fun onFeedSucc(result: Result)
        fun onFeedFail(error: Throwable)
    }
}