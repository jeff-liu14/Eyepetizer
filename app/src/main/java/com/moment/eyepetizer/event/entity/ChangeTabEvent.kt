package com.moment.eyepetizer.event.entity

/**
 * Created by moment on 2018/2/6.
 */

class ChangeTabEvent(tagIndex: Int) {
    var tagIndex: Int = 0

    init {
        this.tagIndex = tagIndex
    }
}
