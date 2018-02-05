package com.moment.eyepetizer.home

import android.view.View
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.event.RxBus
import com.moment.eyepetizer.event.entity.RefreshEvent
import kotlinx.android.synthetic.main.category_fragment.*

/**
 * Created by moment on 2018/2/2.
 */

class CategoryFragment(tab: String) : BaseFragment() {
    var currentTab: String? = tab

    override fun getLayoutId(): Int {
        return R.layout.category_fragment
    }

    override fun initView() {
        tv_test.text = currentTab + ""
        swipeRefreshLayout.setOnRefreshListener {
            RxBus.getDefault().post(RefreshEvent())
            swipeRefreshLayout.postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 1000)

        }
    }

    override fun initData() {
    }

}