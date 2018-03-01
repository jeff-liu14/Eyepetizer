package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */

interface CategoryContract {
    interface CategoryPresenter : BasePresenter {
        fun category(id: Int, start: Int, num: Int): Disposable
    }

    interface CategoryView : BaseView<CategoryPresenter> {
        fun onCategorySucc(result: Result)
        fun onCategoryFail(error: Throwable?)
    }
}