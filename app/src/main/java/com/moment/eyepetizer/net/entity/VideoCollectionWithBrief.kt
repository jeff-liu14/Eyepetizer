package com.moment.eyepetizer.net.entity

/**
 * Created by moment on 2018/2/1.
 */

class VideoCollectionWithBrief {
    var dataType: String? = null
    var header: Header? = null
    var itemList: List<ItemList>? = null
    var count: Int = 0
    var adTrack: String? = null


    class ItemList {
        var type: String? = null
        var tag: String? = null
        var id: Int = 0
        var adIndex: Int = 0
        var data: FollowCard.Data? = null
    }

    class Header {
        var id: Int = 0
        var icon: String? = null
        var iconType: String? = null
        var title: String? = null
        var subTitle: String? = null
        var description: String? = null
        var actionUrl: String? = null
        var adTrack: String? = null
        var follow: Follow? = null
        var ifPgc: Boolean = false

    }

    class Follow {
        var itemType: String? = null
        var itemId: Int = 0
        var followed: Int = 0
    }
}