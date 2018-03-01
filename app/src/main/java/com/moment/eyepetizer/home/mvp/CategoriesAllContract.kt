package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/12.
 */

interface CategoriesAllContract {

    interface CategoriesAllPresenter : BasePresenter {
        fun categoriesAll(): Disposable
    }

    interface CategoriesAllView : BaseView<CategoriesAllContract.CategoriesAllPresenter> {
        fun onCategoriesAllSucc(result: Result)
        fun onCategoriesAllFail(error: Throwable?)
    }
}