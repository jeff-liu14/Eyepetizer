package com.moment.eyepetizer.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by moment on 2018/2/2.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initEvent()
        initView()
        initData()
    }


    abstract fun getLayoutId(): Int
    abstract fun initView()

    abstract fun initData()

    fun initEvent() {

    }
}