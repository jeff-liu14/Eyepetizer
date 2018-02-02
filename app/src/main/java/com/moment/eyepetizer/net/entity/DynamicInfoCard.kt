package com.moment.eyepetizer.net.entity

import android.util.Log

/**
 * Created by moment on 2018/2/1.
 */

class DynamicInfoCard {
    var dataType: String? = null
    var dynamicType: String? = null
    var text: String? = null
    var actionUrl: String? = null
    var user: User? = null
    var createDate: Long = 0
    var simpleVideo: SimpleVideo? = null
    var reply: Reply? = null

    class Reply {
        var id: Long = 0
        var videoId: Long = 0
        var videoTitle: String? = null
        var message: String? = null
        var likeCount: Long = 0
        var showConversationButton: Boolean = false
        var parentReplyId: Int = 0
        var rootReplyId: Long = 0
    }

    class SimpleVideo {
        var id: Long = 0
        var title: String? = null
        var description: String? = null
        var cover: FollowCard.Cover? = null
        var category: String? = null
        var playUrl: String? = null
        var duration: Long = 0
        var releaseTime: Long = 0
    }

    class User {
        var uid: Long = 0
        var nickname: String? = null
        var avatar: String? = null
        var userType: String? = null
        var ifPgc: Boolean = false
        var description: String? = null
        var area: String? = null
        var gender: String? = null
        var registDate: Long = 0
        var cover: String? = null
        var actionUrl: String? = null
    }
}