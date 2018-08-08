package com.moment.eyepetizer.net

import com.moment.eyepetizer.net.entity.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by moment on 16/5/24.
 */
object GetDataList {

    fun discovery(callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .discovery()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ discovery -> callBack.onNext(discovery) },
                    { throwable -> callBack.onError(throwable) })
            { callBack.onCompleted() }

    fun allRec(page: Int, callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .allRec(page)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun feed(date: Long, callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .feed(date, 2)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun parmaMap(): HashMap<String, String> {
        var map = HashMap<String, String>()
        map.put("udid", "1dad62050ee54c10965021ed1bff209cdee1f09e")
        map.put("vc", "256")
        map.put("vn", "3.14")
        map.put("deviceModel", "MIX%202")
        map.put("first_channel", "eyepetizer_yingyongbao_market")
        map.put("last_channel", "eyepetizer_yingyongbao_market")
        map.put("system_version_code", "25")
        return map
    }

    fun category(id: Int, start: Int, num: Int, callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .category(id, start, num, parmaMap())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun categories(callBack: CallBack<List<Categories>>): Disposable = RetrofitUtils().with().build()
            .categories()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun follow(start: Int, num: Int, follow: Boolean, startId: Int, callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .follow(start, num, follow, startId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun search(start: Int, num: Int, search: String, callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .search(search, start, num)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun hot(callBack: CallBack<List<String>>): Disposable = RetrofitUtils().with().build()
            .hot()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun categoriesDetail(id: Int, callBack: CallBack<CategoryInfo>): Disposable = RetrofitUtils().with().build()
            .categoriesDetail(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun categoriesTagList(path: String, map: HashMap<String, String>, callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .categoriesTagList(path, map)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun categoriesAll(callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .categoriesAll()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun rankList(callBack: CallBack<RankList>): Disposable = RetrofitUtils().with().build()
            .rankList()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun rankListVideo(path: String, map: HashMap<String, String>, callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .rankListVideo(path, map)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun specialTopics(start: Int, num: Int, callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .specialTopics(start, num)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun tagIndex(id: Int, callBack: CallBack<TagIndex>): Disposable = RetrofitUtils().with().build()
            .tagIndex(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun getDiscussList(path: String, map: HashMap<String, String>, callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .getDiscussList(path, map)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })

    fun related(id: Int, callBack: CallBack<Result>): Disposable = RetrofitUtils().with().build()
            .related(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> callBack.onNext(result) },
                    { throwable: Throwable -> callBack.onError(throwable) },
                    { callBack.onCompleted() })
}

