package com.moment.eyepetizer.event

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay

import io.reactivex.Observable

/**
 * Created by moment on 2017/12/18.
 */

class RxBus {
    private val mBus: Relay<Any> = PublishRelay.create<Any>().toSerialized()

    fun post(obj: Any) = mBus.accept(obj)

    fun <T> toObservable(tClass: Class<T>): Observable<T> = mBus.ofType(tClass)

    fun toObservable(): Observable<Any> = mBus

    fun hasObservers(): Boolean = mBus.hasObservers()


    companion object {
        private val BUS = RxBus()
        @Volatile private var instance: RxBus? = null

        val default: RxBus?
            get() {
                if (instance == null) {
                    synchronized(RxBus::class.java) {
                        if (instance == null) {
                            instance = BUS
                        }
                    }
                }
                return instance
            }
    }
}
