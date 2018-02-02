package com.moment.eyepetizer.net.entity

/**
 * Created by moment on 2018/2/1.
 */
class BriefCard {
    var dataType: String? = null
    var id: Int = 0
    var icon: String? = null
    var iconType: String? = null
    var title: String? = null
    var subTitle: String? = null
    var description: String? = null
    var actionUrl: String? = null
    var adTrack: String? = null
    var ifPgc: Boolean = false
    var follow: Follow? = null

    class Follow {
        var itemType: String? = null
        var itemId: Int = 0
        var followed: Boolean = false
    }
}