package com.moment.eyepetizer.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by moment on 2018/2/2.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initPresenter()
        initEvent()
        initView()
        initData()
    }


    abstract fun getLayoutId(): Int
    abstract fun initView()

    abstract fun initData()

    internal open fun initEvent() = Unit

    internal open fun initPresenter() = Unit
}