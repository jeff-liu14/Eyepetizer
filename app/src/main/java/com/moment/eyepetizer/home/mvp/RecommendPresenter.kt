package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */
class RecommendPresenter(var recommendView: RecommendContract.RecommendView) : RecommendContract.RecommendPresenter {

    init {
        recommendView.setPresenter(this)
    }

    override fun start() = Unit

    override fun allRec(page: Int): Disposable = GetDataList.allRec(page, object : CallBack<Result> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable) = recommendView.onRecommendFail(e)

        override fun onNext(t: Result) = recommendView.onRecommendSucc(t)

    })

}