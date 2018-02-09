package com.moment.eyepetizer.search.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/8.
 */

interface SearchContract {

    interface SearchPresenter : BasePresenter {
        fun search(start: Int, num: Int, search: String): Disposable
    }

    interface SearchView : BaseView<SearchPresenter> {
        fun onSearchSucc(result: Result)
    }

    interface SearchHotPresenter : BasePresenter {
        fun hot(): Disposable
    }

    interface SearchHotView : BaseView<SearchHotPresenter> {
        fun onHotSucc(tags: List<String>)
    }
}