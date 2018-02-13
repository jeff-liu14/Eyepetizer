package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.RankList
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/12.
 */

class RankListPresenter(var rankListDetailView: RankListContract.RankListView) : RankListContract.RankListPresenter {

    init {
        rankListDetailView.setPresenter(this)
    }

    override fun start() = Unit

    override fun rankList(): Disposable = GetDataList.rankList(object : CallBack<RankList> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable) = rankListDetailView.onRankListFail(e)

        override fun onNext(t: RankList) = rankListDetailView.onRankListSucc(t)

    })
}