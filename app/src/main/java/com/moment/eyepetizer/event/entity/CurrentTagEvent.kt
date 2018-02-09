package com.moment.eyepetizer.event.entity

/**
 * Created by moment on 2018/2/6.
 */

class CurrentTagEvent(tag: String, forceRefresh: Boolean) {
    var tag: String? = null
    var isForceRefresh: Boolean = false

    init {
        this.tag = tag
        this.isForceRefresh = forceRefresh
    }
}
