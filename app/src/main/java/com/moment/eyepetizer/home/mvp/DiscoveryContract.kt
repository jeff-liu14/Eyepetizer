package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */

interface DiscoveryContract {
    interface DiscoveryPresenter : BasePresenter {
        fun discovery(): Disposable
    }

    interface DiscoveryView : BaseView<DiscoveryPresenter> {
        fun onDiscoverySucc(result: Result)
        fun onDiscoveryFail(error: Throwable)
    }
}