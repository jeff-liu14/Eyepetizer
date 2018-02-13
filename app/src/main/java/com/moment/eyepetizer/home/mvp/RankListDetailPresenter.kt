package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/12.
 */

class RankListDetailPresenter(var rankListDetailView: RankListDetailContract.RankListView) : RankListDetailContract.RankListPresenter {

    init {
        rankListDetailView.setPresenter(this)
    }

    override fun start() = Unit

    override fun rankListVideo(path: String, map: HashMap<String, String>): Disposable
            = GetDataList.rankListVideo(path, map, object : CallBack<Result> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable) = rankListDetailView.onRankListFail(e)

        override fun onNext(t: Result) = rankListDetailView.onRankListSucc(t)

    })

}