package com.moment.eyepetizer.net.entity

/**
 * Created by moment on 2018/2/1.
 */

class SquareCardCollection {
    var dataType: String? = null
    var header: FollowCard.Header? = null
    var itemList: List<ItemList>? = null
    var count: Int = 0
    var adTrack: String? = null

    class ItemList {
        var type: String? = null
        var tag: String? = null
        var id: Int = 0
        var adIndex: Int = 0
        var data: Data? = null
    }

    class Data {
        var dataType: String? = null
        var id: Int = 0
        var title: String? = null
        var description: String? = null
        var image: String? = null
        var actionUrl: String? = null
        var adTrack: String? = null
        var shade: Boolean = false
        var header: String? = null
        var label: Label? = null
        var labelList: List<String>? = null
    }

    class Label {
        var text: String? = null
        var card: String? = null
        var detail: String? = null
    }
}