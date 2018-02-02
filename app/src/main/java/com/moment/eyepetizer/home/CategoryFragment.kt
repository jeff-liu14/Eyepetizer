package com.moment.eyepetizer.home

import android.view.View
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
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
    }

    override fun initData() {
    }

}