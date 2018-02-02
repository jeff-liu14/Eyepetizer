package com.moment.eyepetizer

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import kotlin.reflect.KProperty

/**
 * Created by moment on 2018/2/2.
 */

class MyApplication : Application() {
    //    private var myApplication: MyApplication? = null
    private val handler = Handler() // 主线程消息处理

    companion object {
//        private var instance by lazy { MyApplication() }
    }

    fun getInstance(): MyApplication? {
        return null
//        return instance
    }


    fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    fun getScreenHeigh(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.height
    }

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * 支持非ui线程Toast，可避免多个Toast队列等待
     *
     * @param resId
     */
    fun showToast(resId: Int) {
        showToast(resources.getString(resId))
    }

    /**
     * 支持非ui线程Toast，可避免多个Toast队列等待
     *
     * @param text
     */
    fun showToast(text: CharSequence) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            handler.post(Runnable { Toast.makeText(MyApplication().getInstance(), text, Toast.LENGTH_SHORT) })
        } else {
            Toast.makeText(MyApplication().getInstance(), text, Toast.LENGTH_SHORT)
        }
    }

    /**
     * 显示测试时的提示信息
     *
     * @param text
     */
    fun showTestToast(text: CharSequence) {
        if (!BuildConfig.DEBUG) {
            return
        }
        showToastCenter(text, Toast.LENGTH_LONG)
    }

    /**
     * 支持非ui线程Toast，可避免多个Toast队列等待
     *
     * @param text
     */
    fun showToastCenter(text: CharSequence) {
        showToastCenter(text, Toast.LENGTH_SHORT)
    }

    private fun showToastCenter(text: CharSequence, duration: Int) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            handler.post(Runnable { Toast.makeText(MyApplication().getInstance(), text, Toast.LENGTH_LONG) })
        } else {
            Toast.makeText(MyApplication().getInstance(), text, Toast.LENGTH_LONG)
        }
    }
}
