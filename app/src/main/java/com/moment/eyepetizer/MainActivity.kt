package com.moment.eyepetizer

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.example.sdkmanager.SdCardManager
import com.moment.eyepetizer.follow.FollowFragment
import com.moment.eyepetizer.home.HomeFragment
import com.moment.eyepetizer.mine.MineFragment
import com.moment.eyepetizer.notification.NotificationFragment
import com.moment.eyepetizer.utils.ImageLoad
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        clearState()
        when (v?.id) {
            R.id.rb_follow -> {
                rb_follow.isChecked = true
                rb_follow.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(followFragment)
                        .hide(homeFragment)
                        .hide(mineFragment)
                        .hide(notificationFragment)
                        .commit()
                ImageLoad().clearCache(WeakReference(this@MainActivity.applicationContext))
            }
            R.id.rb_home -> {
                rb_home.isChecked = true
                rb_home.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(homeFragment)
                        .hide(notificationFragment)
                        .hide(mineFragment)
                        .hide(followFragment)
                        .commit()
                ImageLoad().clearCache(WeakReference(this@MainActivity.applicationContext))
            }
            R.id.rb_notification -> {
                rb_notification.isChecked = true
                rb_notification.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(notificationFragment)
                        .hide(followFragment)
                        .hide(mineFragment)
                        .hide(homeFragment)
                        .commit()
                ImageLoad().clearCache(WeakReference(this@MainActivity.applicationContext))
            }
            R.id.rb_mine -> {
                rb_mine.isChecked = true
                rb_mine.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(mineFragment)
                        .hide(followFragment)
                        .hide(homeFragment)
                        .hide(notificationFragment)
                        .commit()
                ImageLoad().clearCache(WeakReference(this@MainActivity.applicationContext))
            }
        }
    }

    private fun clearState() {
        rg_root.clearCheck()
        rb_home.setTextColor(resources.getColor(R.color.gray))
        rb_mine.setTextColor(resources.getColor(R.color.gray))
        rb_notification.setTextColor(resources.getColor(R.color.gray))
        rb_follow.setTextColor(resources.getColor(R.color.gray))
    }

    private var homeFragment: Fragment? = null
    private var followFragment: Fragment? = null
    private var notificationFragment: Fragment? = null
    private var mineFragment: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initFragment(savedInstanceState)
    }

    fun initView() = setRadioButton()

    @SuppressLint("RestrictedApi")
    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            //异常情况
            val mFragments: List<Fragment> = supportFragmentManager.fragments
            for (item in mFragments) {
                if (item is HomeFragment) {
                    homeFragment = item
                }
                if (item is NotificationFragment) {
                    notificationFragment = item
                }
                if (item is FollowFragment) {
                    followFragment = item
                }
                if (item is MineFragment) {
                    mineFragment = item
                }
            }

            for ((index, fragment) in mFragments.withIndex()) {

            }
        } else {
            homeFragment = HomeFragment()
            followFragment = FollowFragment()
            notificationFragment = NotificationFragment()
            mineFragment = MineFragment()
            val fragmentTrans = supportFragmentManager.beginTransaction()
            fragmentTrans.add(R.id.fl_content, homeFragment)
            fragmentTrans.add(R.id.fl_content, followFragment)
            fragmentTrans.add(R.id.fl_content, mineFragment)
            fragmentTrans.add(R.id.fl_content, notificationFragment)
            fragmentTrans.commit()
        }
        supportFragmentManager.beginTransaction().show(homeFragment)
                .hide(notificationFragment)
                .hide(mineFragment)
                .hide(followFragment)
                .commit()
        Log.e("sssss", System.currentTimeMillis().toString())
    }


    private fun setRadioButton() {
        rb_home.isChecked = true
        rb_home.setTextColor(resources.getColor(R.color.black))
        rb_home.setOnClickListener(this)
        rb_notification.setOnClickListener(this)
        rb_follow.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
    }

    private var firstTime: Long = 0
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                val secondTime = System.currentTimeMillis()
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                    firstTime = secondTime
                    return true
                } else {
                    System.exit(0)
                }
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onResume() {
        super.onResume()
        // 初始化当前下载路径
        RxPermissions(this)
                .request(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({ aBoolean ->
                    if (aBoolean) {
                        if (SdCardManager.getInstance().isDiskAvailable) {
                            if (SdCardManager.getInstance().isNullPath) {
                                SdCardManager.getInstance().changePath(SdCardManager.DownloadPath.SDCARD)
                            }
                        } else {
                            SdCardManager.getInstance().changePath(SdCardManager.DownloadPath.CACHE)
                        }
                    } else {
                        SdCardManager.getInstance().changePath(SdCardManager.DownloadPath.CACHE)
                    }
                }, { })

    }
}
