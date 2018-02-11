package com.moment.eyepetizer.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.moment.eyepetizer.home.CategoriesTagListActivity
import com.moment.eyepetizer.mine.WebViewActivity

/**
 * Created by moment on 2018/2/11.
 */

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
        "category" -> {
            val path = uri.pathSegments[0]
            Toast.makeText(context, path.toString() + "", Toast.LENGTH_SHORT).show()
            var intent = Intent(context, CategoriesTagListActivity::class.java)
            var bundle = Bundle()
            bundle.putInt("id", path.toInt())
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}