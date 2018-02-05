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
import android.view.ViewGroup
import com.moment.eyepetizer.event.RxBus
import com.moment.eyepetizer.event.entity.RefreshEvent
import com.moment.eyepetizer.net.entity.Categories
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by moment on 2018/2/2.
 */
class HomeFragment : BaseFragment() {
    private var mFragments = ArrayList<Fragment>()
    var str = arrayOf("发现", "推荐", "日报")
    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    var tabId: Long = 10001

    override fun initView() {
        tv_bar_title.visibility = View.GONE
        tab_layout.visibility = View.VISIBLE
        iv_home.setOnClickListener { Toast.makeText(activity, "home", Toast.LENGTH_SHORT).show() }
        iv_search.setOnClickListener { Toast.makeText(activity, "search", Toast.LENGTH_SHORT).show() }
    }

    override fun initEvent() {
        RxBus.getDefault().toObservable(RefreshEvent::class.java)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Toast.makeText(activity, "refresh", Toast.LENGTH_SHORT).show() }
    }


    private var mTitles: ArrayList<String> = ArrayList()
    private var mAdapter: MyPagerAdapter? = null
    override fun initData() {
        GetDataList.categories(object : CallBack<List<Categories>> {
            override fun onCompleted() = Unit

            override fun onError(e: Throwable) = Toast.makeText(activity, e.message + "", Toast.LENGTH_SHORT).show()

            override fun onNext(t: List<Categories>) {
                var list = t.toMutableList()

                var tabs: MutableList<Categories> = ArrayList<Categories>()
                for (tab in str) {
                    val cate = Categories()
                    cate.name = tab
                    cate.id = tabId
                    tabId++
                    tabs.add(cate)
                }

                list.addAll(0, tabs)
                for (cate in list) {
                    if (cate.id == 10001L) {
                        mFragments.add(DiscoveryFragment())
                    } else {
                        mFragments.add(CategoryFragment(cate.id.toString() + "-" + cate.name!!))
                    }
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

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
//            super.destroyItem(container, position, `object`)
        }
    }
}