package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Categories
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */

interface CategoriesContract {
    interface CategoriesPresenter : BasePresenter {
        fun categories(): Disposable
    }

    interface CategoriesView : BaseView<CategoriesPresenter> {
        fun onCategoriesSucc(result: List<Categories>)
        fun onCategoriesFail(error: Throwable?)
    }
}