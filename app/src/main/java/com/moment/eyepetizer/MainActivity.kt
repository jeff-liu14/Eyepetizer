package com.moment.eyepetizer

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.moment.eyepetizer.follow.FollowFragment
import com.moment.eyepetizer.home.HomeFragment
import com.moment.eyepetizer.mine.MineFragment
import com.moment.eyepetizer.notification.NotificationFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.widget.LinearLayout
import android.util.TypedValue
import java.lang.reflect.AccessibleObject.setAccessible
import android.support.design.widget.TabLayout
import java.lang.reflect.Field
import android.opengl.ETC1.getWidth
import android.widget.TextView


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
//                tv_bar_title.text = "subscription"
//                tv_bar_title.visibility = View.VISIBLE
//                toolbar.visibility = View.VISIBLE
//                tab_layout.visibility = View.GONE
//                iv_search.setImageResource(R.drawable.guide_search)
            }
            R.id.rb_home -> {
//                toolbar.visibility = View.GONE
                rb_home.isChecked = true
                rb_home.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(homeFragment)
                        .hide(notificationFragment)
                        .hide(mineFragment)
                        .hide(followFragment)
                        .commit()
//                tv_bar_title.text = getToday()
//                tv_bar_title.visibility = View.GONE
//                toolbar.visibility = View.VISIBLE
//                tab_layout.visibility = View.VISIBLE
//                iv_search.setImageResource(R.drawable.guide_search)
            }
            R.id.rb_notification -> {
                rb_notification.isChecked = true
                rb_notification.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(notificationFragment)
                        .hide(followFragment)
                        .hide(mineFragment)
                        .hide(homeFragment)
                        .commit()
            }
            R.id.rb_mine -> {
                rb_mine.isChecked = true
                rb_mine.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(mineFragment)
                        .hide(followFragment)
                        .hide(homeFragment)
                        .hide(notificationFragment)
                        .commit()
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

    var homeFragment: Fragment? = null
    var followFragment: Fragment? = null
    var notificationFragment: Fragment? = null
    var mineFragment: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initFragment(savedInstanceState)
    }

    fun initView() {
        setRadioButton()
    }

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
    }


    private fun setRadioButton() {
        rb_home.isChecked = true
        rb_home.setTextColor(resources.getColor(R.color.black))
        rb_home.setOnClickListener(this)
        rb_notification.setOnClickListener(this)
        rb_follow.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
    }

    private fun getToday(): String {
        var list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        var data: Date = Date()
        var calendar: Calendar = Calendar.getInstance()
        calendar.time = data
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return list[index]
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
}
