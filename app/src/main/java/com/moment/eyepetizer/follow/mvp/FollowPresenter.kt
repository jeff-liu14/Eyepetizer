package com.moment.eyepetizer.follow.mvp

import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.disposables.Disposable

/**
 * Created by moment on 2018/2/7.
 */

class FollowPresenter(var followView: FollowContract.FollowView) : FollowContract.FollowPresenter {

    init {
        followView.setPresenter(this)
    }

    override fun follow(start: Int, num: Int, follow: Boolean, startId: Int): Disposable =
            GetDataList.follow(start, num, follow, startId, object : CallBack<Result> {
                override fun onCompleted() = Unit

                override fun onError(e: Throwable?) = followView.onError(e)

                override fun onNext(t: Result) = followView.onFollowSucc(t)

            })

    override fun start() = Unit

}