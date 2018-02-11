package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.CategoryInfo
import io.reactivex.disposables.Disposable


/**
 * Created by moment on 2018/2/11.
 */

class CategoriesDetailPresenter(var categoriesTagListView: CategoriesDetailContract.CategoriesDetailView) : CategoriesDetailContract.CategoriesDetailPresenter {

    init {
        categoriesTagListView.setPresenter(this)
    }

    override fun start() = Unit

    override fun categoriesDetail(id: Int): Disposable =
            GetDataList.categoriesDetail(id, object : CallBack<CategoryInfo> {
                override fun onCompleted() = Unit

                override fun onError(e: Throwable) = categoriesTagListView.onCategoriesDetailFail(e)

                override fun onNext(t: CategoryInfo) = categoriesTagListView.onCategoriesDetailSucc(t)

            })

}