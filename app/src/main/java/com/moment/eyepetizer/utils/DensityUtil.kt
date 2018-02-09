package com.moment.eyepetizer.utils

import android.content.Context

/**
 * Created by moment on 16/5/26.
 */
class DensityUtil {
    companion object {

        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }
    }
}
