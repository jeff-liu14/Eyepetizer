package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/11.
 */

interface RankListDetailContract {
    interface RankListPresenter : BasePresenter {
        fun rankListVideo(path: String, map: HashMap<String, String>): Disposable
    }

    interface RankListView : BaseView<RankListPresenter> {
        fun onRankListSucc(result: Result)
        fun onRankListFail(error: Throwable)
    }
}