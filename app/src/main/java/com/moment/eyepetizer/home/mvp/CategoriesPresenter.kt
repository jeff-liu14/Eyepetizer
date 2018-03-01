package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Categories
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */

class CategoriesPresenter(var categoriesView: CategoriesContract.CategoriesView) : CategoriesContract.CategoriesPresenter {

    init {
        categoriesView.setPresenter(this)
    }

    override fun start() = Unit

    override fun categories(): Disposable = GetDataList.categories(object : CallBack<List<Categories>> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable?) = categoriesView.onCategoriesFail(e)

        override fun onNext(t: List<Categories>) = categoriesView.onCategoriesSucc(t)

    })

}