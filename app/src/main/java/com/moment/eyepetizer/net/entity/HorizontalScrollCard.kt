package com.moment.eyepetizer.net.entity

/**
 * Created by moment on 2018/2/1.
 */
class HorizontalScrollCard {
    var dataType: String? = null
    var count: Int = 0
    var itemList: ItemList? = null


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
        var labelList: List<String>? = null
        var label: Label? = null
        var header: Header? = null
    }

    class Label {
        var text: String? = null
        var card: String? = null
        var detail: String? = null
    }

    class Header {
        var id: Int = 0
        var title: String? = null
        var font: String? = null
        var subTitle: String? = null
        var subTitleFont: String? = null
        var textAlign: String? = null
        var cover: String? = null
        var label: String? = null
        var actionUrl: String? = null
        var labelList: String? = null
        var icon: String? = null
        var description: String? = null
    }
}