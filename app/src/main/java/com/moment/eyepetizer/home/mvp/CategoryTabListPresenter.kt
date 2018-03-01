package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/11.
 */

class CategoryTabListPresenter(var categoryTagListView: CategoryTabListContract.CategoriesTagListView) :
        CategoryTabListContract.CategoriesTagListPresenter {

    init {
        categoryTagListView.setPresenter(this)
    }

    override fun start() = Unit

    override fun categoriesTagList(path: String, map: HashMap<String, String>): Disposable =
            GetDataList.categoriesTagList(path, map, object : CallBack<Result> {
                override fun onCompleted() = Unit

                override fun onError(e: Throwable?) = categoryTagListView.onCategoriesTagFail(e)

                override fun onNext(t: Result) = categoryTagListView.onCategoriesTagSucc(t)
            })

}