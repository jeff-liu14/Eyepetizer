package com.moment.eyepetizer.home

import android.graphics.Typeface
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import kotlinx.android.synthetic.main.home_fragment.*
import android.support.v4.app.FragmentPagerAdapter
import com.moment.eyepetizer.net.entity.Categories


/**
 * Created by moment on 2018/2/2.
 */
class HomeFragment : BaseFragment() {
    private var mFragments = ArrayList<Fragment>()
    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initView() {
        tv_bar_title.visibility = View.GONE
        tab_layout.visibility = View.VISIBLE
        iv_home.setOnClickListener { Toast.makeText(activity, "home", Toast.LENGTH_SHORT).show() }
        iv_search.setOnClickListener { Toast.makeText(activity, "search", Toast.LENGTH_SHORT).show() }
    }

    private var mTitles: ArrayList<String> = ArrayList()
    private var mAdapter: MyPagerAdapter? = null
    override fun initData() {
        GetDataList.categories(object : CallBack<List<Categories>> {
            override fun onCompleted() = Unit

            override fun onError(e: Throwable) = Toast.makeText(activity, e.message + "", Toast.LENGTH_SHORT).show()

            override fun onNext(t: List<Categories>) {
                for (cate in t) {
                    mFragments.add(CategoryFragment(cate.id.toString() + "-" + cate.name!!))
                    mTitles.add(cate.name!!)
                }
                mAdapter = MyPagerAdapter(fragmentManager)
                viewpager.adapter = mAdapter
                val stringArray = mTitles.toArray(arrayOfNulls<String>(0))
                tab_layout.setViewPager(viewpager, stringArray)
                viewpager.offscreenPageLimit = mTitles.size
                viewpager.currentItem = 0
            }

        })
    }

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTitles.get(position)
        }

        override fun getItem(position: Int): Fragment {
            return mFragments.get(position)
        }
    }
}