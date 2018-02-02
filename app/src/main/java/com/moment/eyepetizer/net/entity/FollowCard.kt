package com.moment.eyepetizer.net.entity

/**
 * Created by moment on 2018/2/1.
 */

class FollowCard {
    var dataType: String? = null
    var adTrack: String? = null
    var header: Header? = null
    var content: Content? = null

    class Content {
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
        var slogan: String? = null
        var description: String? = null
        var provider: Provider? = null
        var category: String? = null
        var author: Author? = null
        var cover: Cover? = null
        var playUrl: String? = null
        var thumbPlayUrl: String? = null
        var duration: Int = 0
        var webUrl: WebUrl? = null
        var releaseTime: Long = 0
        var library: String? = null
        var playInfo: List<PlayInfo>? = null
        var consumption: Consumption? = null
        var campaign: String? = null
        var waterMarks: String? = null
        var adTrack: String? = null
        var tags: List<Tags>? = null
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
        var labelList: String? = null
        var descriptionEditor: String? = null
        var collected: Boolean = false
        var played: Boolean = false
        var subtitles: List<String>? = null
        var lastViewTime: String? = null
        var playlists: String? = null
        var src: String? = null
    }


    class Tags {
        var id: Int = 0
        var name: String? = null
        var actionUrl: String? = null
        var adTrack: String? = null
        var desc: String? = null
        var bgPicture: String? = null
        var headerImage: String? = null
        var tagRecType: String? = null
    }

    class Consumption {
        var collectionCount: Int = 0
        var shareCount: Int = 0
        var replyCount: Int = 0
    }

    class PlayInfo {
        var height: Int = 0
        var width: Int = 0
        var name: String? = null
        var type: String? = null
        var url: String? = null
        var urlList: List<UrlList>? = null
    }

    class UrlList {
        var name: String? = null
        var url: String? = null
        var size: Long = 0
    }

    class WebUrl {
        var raw: String? = null
        var forWeibo: String? = null
    }

    class Cover {
        var feed: String? = null
        var detail: String? = null
        var blurred: String? = null
        var sharing: String? = null
        var homepage: String? = null
    }

    class Provider {
        var name: String? = null
        var alias: String? = null
        var icon: String? = null
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
        var follow: Follow? = null
        var shield: Shield? = null
        var approvedNotReadyVideoCount: Int = 0
        var ifPgc: Boolean = false
    }

    class Shield {
        var itemType: String? = null
        var itemId: Int = 0
        var shielded: Boolean = false
    }

    class Follow {
        var itemType: String? = null
        var itemId: Int = 0
        var followed: Boolean = false
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
        var iconType: String? = null
        var description: String? = null
        var time: Long = 0
        var showHateVideo: Boolean = false
    }
}