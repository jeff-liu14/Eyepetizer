package com.moment.eyepetizer.net.entity

import org.json.JSONObject
import java.util.*

/**
 * Created by moment on 2018/2/1.
 */

class Result {
    var itemList: List<ItemList>? = null
    var count: Int = 0
    var total: Int = 0
    var nextPageUrl: String? = null
    var adExist: Boolean = false
    var updateTime: Object? = null
    var refreshCount: Int? = 0
    var lastStartId: Int? = 0

    class ItemList {
        var type: String? = null
        var tag: String? = null
        var id: Int = 0
        var adIndex: Int = 0
        /**
         * horizontalScrollCard、textCard、briefCard、followCard、
         * videoSmallCard、squareCardCollection、videoCollectionWithBrief、DynamicInfoCard、
         * banner
         */
        var data: Any? = null

        override fun toString(): String {
            return "ItemList(type=$type, tag=$tag, id=$id, adIndex=$adIndex, data=$data)"
        }


    }

    override fun toString(): String {
        return "Discovery(itemList=${itemList.toString()}, count=$count, total=$total, nextPageUrl=$nextPageUrl, adExist=$adExist)"
    }


}
