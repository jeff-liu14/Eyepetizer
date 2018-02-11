package com.moment.eyepetizer.utils

import android.content.Context
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import com.moment.eyepetizer.net.entity.Result
import java.io.UnsupportedEncodingException

/**
 * Created by moment on 2018/2/9.
 */
fun getScreenWidth(context: Context): Int {
    val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return wm.defaultDisplay.width
}

fun getScreenHeight(context: Context): Int {
    val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return wm.defaultDisplay.height
}

fun getMultiType(position: Int, datas: ArrayList<Result.ItemList>): Int {
    var type: String = datas!![position].type!!
    return type.hashCode()
}

fun unbindDrawables(view: View) {
    if (view.getBackground() != null) {
        view.getBackground().setCallback(null)
    }
    if (view is ViewGroup && view !is AdapterView<*>) {
        for (i in 0 until (view as ViewGroup).childCount) {
            unbindDrawables((view as ViewGroup).getChildAt(i))
        }
        (view as ViewGroup).removeAllViews()
    }
}

val UTF_8 = "utf-8"

/**
 * 编码字符串
 *
 * @param data 待编码字符串
 * @return 结果字符串
 */
fun encodeToString(data: String?): String? {
    try {
        if (data != null) {
            return encodeToString(data.toByteArray(charset(UTF_8)))
        }
    } catch (e: UnsupportedEncodingException) {
        //never in
        e.printStackTrace()
    }

    return null
}

/**
 * 编码数据
 *
 * @param data 字节数组
 * @return 结果字符串
 */
fun encodeToString(data: ByteArray): String {
    return Base64.encodeToString(data, Base64.URL_SAFE or Base64.NO_WRAP)
}

/**
 * 解码数据
 *
 * @param data 编码过的字符串
 * @return 原始数据
 */
fun decode(data: String?): ByteArray {
    return Base64.decode(data, Base64.URL_SAFE or Base64.NO_WRAP)
}