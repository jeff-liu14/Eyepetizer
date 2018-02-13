package com.moment.eyepetizer.home.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/13.
 */

class DiscussListPresenter(var discussListView: DiscussListContract.DiscussListView) : DiscussListContract.DiscussListPresenter {

    init {
        discussListView.setPresenter(this)
    }

    override fun start() = Unit

    override fun getDiscussList(path: String, map: HashMap<String, String>): Disposable =
            GetDataList.getDiscussList(path, map, object : CallBack<Result> {
                override fun onCompleted() = Unit

                override fun onError(e: Throwable) = discussListView.onDiscussListFail(e)

                override fun onNext(t: Result) = discussListView.onDiscussListSucc(t)

            })

}