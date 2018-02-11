package com.moment.eyepetizer.mine

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.KeyEvent
import android.webkit.*
import android.widget.ImageView
import android.widget.TextView
import com.moment.eyepetizer.R
import com.moment.eyepetizer.utils.decode
import java.util.HashMap

/**
 * Created by moment on 2018/2/11.
 */

class WebViewActivity : AppCompatActivity() {
    private var url: String? = null
    private var webView: WebView? = null
    private var back: ImageView? = null
    private var titleText: TextView? = null
    private var chromeClient: MyWebChromeClient? = null

    private val headers: Map<String, String>
        get() {
            val map = HashMap<String, String>()
            map.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            map.put("Connection", "keep-alive")
            map.put("Accept", "*/*")
            map.put("Cookie", "add cookies here")
            map.put("Content-Encoding", "gzip")
            map.put("clientversion", "")
            map.put("devicetype", 3.toString() + "")
            map.put("deviceinfo", "android")
            map.put("qudao", "")
            map.put("clientid", "")
            map.put("devicetoken", "")
            return map

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_profile_webview)
        webView = findViewById(R.id.webView) as WebView
        back = findViewById(R.id.back) as ImageView
        titleText = findViewById(R.id.title) as TextView
        webView!!.settings.defaultTextEncodingName = "UTF-8"
        val bundle = intent.extras ?: return
        url = bundle.getString("urlBase64")
        if (TextUtils.isEmpty(url)) {
            url = intent.getStringExtra("url")
        } else {
            url = String(decode(url))
        }
        val title = bundle.getString("title")
        titleText!!.text = title
        CookieSyncManager.createInstance(this)
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.removeSessionCookie()//移除
        CookieSyncManager.getInstance().sync()
        CookieSyncManager.createInstance(this)
        CookieManager.getInstance().removeAllCookie()
        initializeWebView(webView!!)
        webView!!.loadUrl(url, headers)
        back!!.setOnClickListener {
            if (webView!!.canGoBack()) {
                webView!!.goBack()//返回上一页面
            } else {
                finish()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView!!.canGoBack()) {
                webView!!.goBack()//返回上一页面
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initializeWebView(mWebView: WebView) {
        val ws = mWebView.settings
        ws.useWideViewPort = true
        ws.loadWithOverviewMode = true// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.javaScriptEnabled = true
        ws.setSupportMultipleWindows(true)
        ws.domStorageEnabled = true
        // ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);// 排版适应屏幕
        mWebView.settings.loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19
        ws.cacheMode = WebSettings.LOAD_NO_CACHE
        deleteDatabase("WebView.db")
        deleteDatabase("WebViewCache.db")
        mWebView.clearHistory()
        mWebView.clearFormData()
        chromeClient = MyWebChromeClient()
        mWebView.webViewClient = MyWebViewClient()
        mWebView.webChromeClient = chromeClient
    }

    private inner class MyWebChromeClient : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String?) {
            var title = title
            super.onReceivedTitle(view, title)
            if (title != null && title.length > 10) {
                title = title.substring(0, 10) + ".."
            }
//            titleText!!.text = title
        }
    }

    // 监听
    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url, headers)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            if (!webView!!.settings.loadsImagesAutomatically) {
                webView!!.settings.loadsImagesAutomatically = true
            }
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) =
                handler.proceed()

    }

    override fun onDestroy() {
        super.onDestroy()
        webView!!.loadUrl("about:blank")
        webView!!.stopLoading()
        webView!!.webChromeClient = null
        webView!!.webViewClient = null
        webView!!.destroy()
        webView = null
    }

    override fun onPause() {
        super.onPause()
        webView!!.onPause()
        webView!!.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        webView!!.onResume()
        webView!!.resumeTimers()
    }
}
