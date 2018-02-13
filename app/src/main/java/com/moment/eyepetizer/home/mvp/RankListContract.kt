package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.RankList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/11.
 */

interface RankListContract {
    interface RankListPresenter : BasePresenter {
        fun rankList(): Disposable
    }

    interface RankListView : BaseView<RankListPresenter> {
        fun onRankListSucc(result: RankList)
        fun onRankListFail(error: Throwable)
    }
}