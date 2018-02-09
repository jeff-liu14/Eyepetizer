package com.moment.eyepetizer.notification

import android.content.Intent
import android.graphics.Typeface
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.search.SearchActivity
import kotlinx.android.synthetic.main.notification_fragment.*

/**
 * Created by moment on 2018/2/2.
 */
class NotificationFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.notification_fragment

    override fun initView() {
        tv_bar_title.typeface = Typeface.createFromAsset(activity.assets, "fonts/Lobster-1.4.otf")
    }

    override fun initData() = iv_search.setOnClickListener {
        var intent = Intent(activity, SearchActivity::class.java)
        startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_in_top)
    }

}