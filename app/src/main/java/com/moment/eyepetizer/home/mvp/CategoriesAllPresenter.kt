package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/12.
 */

class CategoriesAllPresenter(val categoriesAllView: CategoriesAllContract.CategoriesAllView) : CategoriesAllContract.CategoriesAllPresenter {

    init {
        categoriesAllView.setPresenter(this)
    }

    override fun start() = Unit

    override fun categoriesAll(): Disposable = GetDataList.categoriesAll(object : CallBack<Result> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable) = categoriesAllView.onCategoriesAllFail(e)

        override fun onNext(t: Result) = categoriesAllView.onCategoriesAllSucc(t)

    })

}