package com.moment.eyepetizer.follow.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */

interface FollowContract {
    interface FollowPresenter : BasePresenter {
        fun follow(start: Int, num: Int, follow: Boolean, startId: Int): Disposable
    }

    interface FollowView : BaseView<FollowPresenter> {
        fun onFollowSucc(result: Result)
        fun onError(error: Throwable)
    }
}