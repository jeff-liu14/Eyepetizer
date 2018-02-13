package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/12.
 */

class SpecialTopicsPresenter(var specialTopicsView: SpecialTopicsContract.SpecialTopicsView) : SpecialTopicsContract.SpecialTopicsPresenter {

    init {
        specialTopicsView.setPresenter(this)
    }

    override fun start() = Unit

    override fun specialTopics(start: Int, num: Int): Disposable
            = GetDataList.specialTopics(start, num, object : CallBack<Result> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable) = specialTopicsView.onSpecialTopicsFail(e)

        override fun onNext(t: Result) = specialTopicsView.onSpecialTopicsSucc(t)

    })

}