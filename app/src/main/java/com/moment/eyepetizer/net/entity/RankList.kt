package com.moment.eyepetizer.net.entity

/**
 * Created by moment on 2018/2/12.
 */

class RankList {

    var tabInfo: TabInfo? = null

    class TabInfo {
        var defaultIdx: Int = 0
        var tabList: List<TabList>? = null
    }

    class TabList {
        var id: Int = 0
        var name: String? = null
        var apiUrl: String? = null
    }
}