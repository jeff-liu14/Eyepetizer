package com.moment.eyepetizer.utils

import android.net.Uri
import android.util.Log

/**
 * Created by moment on 2018/2/2.
 */

class UriUtils {

    class FeedParse {
        var data: Long = 0
        var num: Int = 0
    }

    class CategoryParse {
        var start: Int = 0
        var num: Int = 0
    }

    class FollowParse {
        var start: Int = 0
        var num: Int = 0
        var follow: Boolean = false
        var startId: Int = 0
    }

    class SearchParse {
        var start: Int = 0
        var num: Int = 0
        var query: String? = null
    }

    class CategoriesTagListParse {
        var map: HashMap<String, String>? = null
    }

    open fun parseCategoriesTagListUri(url: String): CategoriesTagListParse {
        var uri: Uri? = Uri.parse(url)
        var map: HashMap<String, String> = HashMap()
        for (name in uri!!.queryParameterNames) {
            map.put(name, uri!!.getQueryParameter(name))
        }
        var tagListParse = CategoriesTagListParse()
        tagListParse.map = map
        return tagListParse
    }

    open fun parseSearchUri(url: String): SearchParse {
        var uri: Uri? = Uri.parse(url)
        var start: Int = uri!!.getQueryParameter("start").toInt()
        var num: Int = uri!!.getQueryParameter("num").toInt()
        var query: String = uri!!.getQueryParameter("query")
        var searchParse = SearchParse()
        searchParse.start = start
        searchParse.num = num
        searchParse.query = query
        return searchParse
    }

    open fun parseFeedUri(url: String): FeedParse {
        var uri: Uri? = Uri.parse(url)
        var date: Long = uri!!.getQueryParameter("date").toLong()
        var num: Int = uri!!.getQueryParameter("num").toInt()
        var feedParse = FeedParse()
        feedParse.data = date
        feedParse.num = num
        return feedParse
    }

    open fun parseCategoryUri(url: String): CategoryParse {
        var uri: Uri? = Uri.parse(url)
        var start: Int = uri!!.getQueryParameter("start").toInt()
        var num: Int = uri!!.getQueryParameter("num").toInt()
        var path: String = uri!!.path
        var category = CategoryParse()
        category.start = start
        category.num = num
        return category
    }

    open fun parseFollowUri(url: String): FollowParse {
        var uri: Uri? = Uri.parse(url)
        var start: Int = uri!!.getQueryParameter("start").toInt()
        var num: Int = uri!!.getQueryParameter("num").toInt()
        var follow: Boolean = uri!!.getQueryParameter("follow").toBoolean()
        var startId: Int = uri!!.getQueryParameter("startId").toInt()

        var followparse = FollowParse()
        followparse.start = start
        followparse.num = num
        followparse.follow = follow
        followparse.startId = startId
        return followparse
    }
}