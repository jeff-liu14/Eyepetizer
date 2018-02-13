package com.moment.eyepetizer.home.mvp

import com.cn.maimeng.news.base.BasePresenter
import com.cn.maimeng.news.base.BaseView
import com.moment.eyepetizer.net.entity.TagIndex
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/13.
 */

interface TagIndexContract {

    interface TagIndexPresenter : BasePresenter {
        fun tagIndex(id: Int): Disposable
    }

    interface TagIndexView : BaseView<TagIndexPresenter> {
        fun onTagIndexSucc(tagIndex: TagIndex)
        fun onTagIndexFail(throwable: Throwable)
    }
}