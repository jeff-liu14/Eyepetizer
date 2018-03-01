package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/12.
 */

interface SpecialTopicsContract {

    interface SpecialTopicsPresenter : BasePresenter {
        fun specialTopics(start: Int, num: Int): Disposable
    }

    interface SpecialTopicsView : BaseView<SpecialTopicsPresenter> {
        fun onSpecialTopicsSucc(result: Result)
        fun onSpecialTopicsFail(throwable: Throwable?)
    }
}