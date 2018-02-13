package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/13.
 */

interface DiscussListContract {

    interface DiscussListPresenter : BasePresenter {
        fun getDiscussList(path: String, map: HashMap<String, String>): Disposable
    }

    interface DiscussListView : BaseView<DiscussListPresenter> {
        fun onDiscussListSucc(result: Result)
        fun onDiscussListFail(throwable: Throwable)
    }
}