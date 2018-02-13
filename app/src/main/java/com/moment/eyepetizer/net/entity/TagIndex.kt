package com.moment.eyepetizer.net.entity

/**
 * Created by moment on 2018/2/13.
 */

class TagIndex {

    var tabInfo: TabInfo? = null
    var tagInfo: TagInfo? = null

    class TabInfo {
        var defaultIdx: Int = 0
        var tabList: List<TabList>? = null
    }

    class TabList {
        var id: Int = 0
        var name: String? = null
        var apiUrl: String? = null
    }

    class TagInfo {
        var dataType: String? = null
        var id: Int = 0
        var name: String? = null
        var description: String? = null
        var headerImage: String? = null
        var bgPicture: String? = null
        var actionUrl: String? = null
        var recType: Int = 0
        var tagFollowCount: Int = 0
        var tagVideoCount: Int = 0
        var tagDynamicCount: Int = 0
        var follow: Follow? = null
    }

    class Follow {
        var itemType: String? = null
        var itemId: Int = 0
        var followed: Boolean = false
    }

}