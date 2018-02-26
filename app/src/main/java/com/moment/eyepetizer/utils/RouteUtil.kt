package com.moment.eyepetizer.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.moment.eyepetizer.event.RxBus
import com.moment.eyepetizer.event.entity.ChangeTabEvent
import com.moment.eyepetizer.home.*
import com.moment.eyepetizer.mine.WebViewActivity
import java.net.URLEncoder

/**
 * Created by moment on 2018/2/11.
 */

fun parseWebView(title: String, url: String): String {
    var builder = StringBuilder()
    builder.append("eyepetizer://webview/?title=")
    builder.append(URLEncoder.encode(title, "utf-8"))
    builder.append("&")
    builder.append("url=")
    builder.append(URLEncoder.encode(url, "utf-8"))
    return builder.toString()
}

fun composeUri(path: String, map: HashMap<String, String>): String {
    var builder = StringBuilder()
    builder.append("eyepetizer://")
    builder.append(path + "/?")
    for (key in map.keys) {
        builder.append(key + "=")
        builder.append(map[key])
        builder.append("&")
    }
    return builder.toString()
}

fun parseUri(context: Context, url: String) {
    var uri: Uri = Uri.parse(url)
    var path = uri.host
    when (path) {
        "webview" -> {
            val title = uri.getQueryParameter("title")
            val url = uri.getQueryParameter("url")
            var intent = Intent(context, WebViewActivity::class.java)
            var bundle = Bundle()
            bundle.putString("urlBase64", encodeToString(url))
            bundle.putString("title", title)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    //分类详情
        "category" -> {
            val path = uri.pathSegments[0]
            var intent = Intent(context, CategoriesTagListActivity::class.java)
            var bundle = Bundle()
            bundle.putInt("id", path.toInt())
            bundle.putString("title", uri.getQueryParameter("title"))
            if (uri.getQueryParameter("tabIndex") != null) {
                bundle.putInt("tabIndex", uri.getQueryParameter("tabIndex").toInt())
            }
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    //全部分类
        "categories" -> {
            var intent = Intent(context, CategoriesAllActivity::class.java)
            context.startActivity(intent)
        }
    //排行榜
        "ranklist" -> {
            var intent = Intent(context, RankListActivity::class.java)
            context.startActivity(intent)
        }
    //近期专题
        "campaign" -> {
            var intent = Intent(context, SpecialTopicsActivity::class.java)
            intent.putExtra("title", uri.getQueryParameter("title"))
            context.startActivity(intent)
        }
    //360全景视频
        "tag" -> {
            var intent = Intent(context, TagIndexActivity::class.java)
            var bundle = Bundle()
            var id = uri.pathSegments[0]
            bundle.putInt("id", id.toInt())
            bundle.putString("title", uri.getQueryParameter("title"))
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    //近期话题
        "common" -> {
            var intent = Intent(context, DiscussListActivity::class.java)
            var bundle = Bundle()
            bundle.putString("url", uri.getQueryParameter("url"))
            bundle.putString("title", uri.getQueryParameter("title"))
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    //切换tab
        "feed" -> {
            var tabIndex = uri.getQueryParameter("tabIndex").toInt()
            RxBus.default!!.post(ChangeTabEvent(tabIndex))
        }

        "video" -> {
            var intent = Intent(context, TagIndexActivity::class.java)
            var bundle = Bundle()
            bundle.putInt("id", uri.getQueryParameter("id").toInt())
            bundle.putString("title", uri.getQueryParameter("title"))
            bundle.putString("description", uri.getQueryParameter("description"))
            bundle.putString("bg", uri.getQueryParameter("bg"))
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
        else -> {
            Toast.makeText(context, path, Toast.LENGTH_SHORT).show()
        }
    }
}