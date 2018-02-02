package com.moment.eyepetizer.net.entity

/**
 * Created by moment on 2018/2/1.
 */

class VideoSmallCard {

    var dataType: String? = null
    var id: Long = 0
    var title: String? = null
    var slogan: String? = null
    var description: String? = null
    var provider: Provider? = null
    var category: String? = null
    var cover: FollowCard.Cover? = null
    var playUrl: String? = null
    var thumbPlayUrl: String? = null
    var duration: Long = 0
    var webUrl: WebUrl? = null
    var releaseTime: Long = 0
    var library: String? = null
    var playInfo: List<FollowCard.PlayInfo>? = null
    var consumption: FollowCard.Consumption? = null
    var campaign: String? = null
    var waterMarks: String? = null
    var adTrack: String? = null
    var tags: List<FollowCard.Tags>? = null
    var type: String? = null
    var titlePgc: String? = null
    var descriptionPgc: String? = null
    var remark: String? = null
    var idx: Int = 0
    var shareAdTrack: String? = null
    var favoriteAdTrack: String? = null
    var webAdTrack: String? = null
    var date: Long = 0
    var promotion: String? = null
    var label: String? = null
    var labelList: List<String>? = null
    var descriptionEditor: String? = null
    var collected: Boolean = false
    var played: Boolean = false
    var subtitles: List<String>? = null
    var lastViewTime: String? = null
    var playlists: String? = null
    var src: String? = null


    class WebUrl {
        var raw: String? = null
        var forWeibo: String? = null
    }

    class Author {
        var id: Int = 0
        var icon: String? = null
        var name: String? = null
        var description: String? = null
        var link: String? = null
        var latestReleaseTime: Long = 0
        var videoNum: Int = 0
        var adTrack: String? = null
        var follow: FollowCard.Follow? = null
        var shield: FollowCard.Shield? = null
        var approvedNotReadyVideoCount: Int = 0
        var ifPgc: Boolean = false
    }

    class Provider {
        var name: String? = null
        var alias: String? = null
        var icon: String? = null
    }

}