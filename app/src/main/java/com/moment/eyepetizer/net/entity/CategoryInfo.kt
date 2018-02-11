package com.moment.eyepetizer.net.entity

/**
 * Created by moment on 2018/2/11.
 */

class CategoryInfo {

    var categoryInfo: CategoryInfo? = null
    var tabInfo: TabInfo? = null

    class CategoryInfo {
        var dataType: String? = null
        var id: Long? = null
        var name: String? = null
        var description: String? = null
        var headerImage: String? = null
        var actionUrl: String? = null
        var follow: Follow? = null

    }

    class Follow {
        var itemType: String? = null
        var itemId: Int? = null
        var followed: Boolean = false
    }

    class TabInfo {
        var defaultIdx: Int? = 0
        var tabList: List<TabList>? = null

    }

    class TabList {
        var id: Int = 0
        var name: String? = null
        var apiUrl: String? = null
    }
}