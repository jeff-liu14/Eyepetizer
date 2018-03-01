package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.TagIndex
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/13.
 */

class TagIndexPresenter(var tagIndexView: TagIndexContract.TagIndexView) : TagIndexContract.TagIndexPresenter {

    init {
        tagIndexView.setPresenter(this)
    }

    override fun start() = Unit

    override fun tagIndex(id: Int): Disposable = GetDataList.tagIndex(id, object : CallBack<TagIndex> {
        override fun onCompleted() = Unit

        override fun onError(e: Throwable?) = tagIndexView.onTagIndexFail(e)

        override fun onNext(t: TagIndex) = tagIndexView.onTagIndexSucc(t)

    })

}