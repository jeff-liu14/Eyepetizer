package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.CategoryInfo
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/11.
 */

interface CategoriesDetailContract {
    interface CategoriesDetailPresenter : BasePresenter {
        fun categoriesDetail(id: Int): Disposable
    }

    interface CategoriesDetailView : BaseView<CategoriesDetailPresenter> {
        fun onCategoriesDetailSucc(result: CategoryInfo)
        fun onCategoriesDetailFail(error: Throwable)
    }
}