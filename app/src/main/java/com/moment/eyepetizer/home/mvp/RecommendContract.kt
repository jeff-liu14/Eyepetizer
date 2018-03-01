package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */

interface RecommendContract {
    interface RecommendPresenter : BasePresenter {
        fun allRec(page: Int): Disposable
    }

    interface RecommendView : BaseView<RecommendPresenter> {
        fun onRecommendSucc(result: Result)
        fun onRecommendFail(error: Throwable?)
    }
}