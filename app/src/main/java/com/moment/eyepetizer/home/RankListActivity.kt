package com.moment.eyepetizer.home

import android.graphics.Typeface
import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseActivity
import com.moment.eyepetizer.home.mvp.RankListContract
import com.moment.eyepetizer.home.mvp.RankListPresenter
import com.moment.eyepetizer.net.entity.RankList
import com.moment.eyepetizer.utils.UriUtils
import kotlinx.android.synthetic.main.ranklist_activity.*
import java.io.File

/**
 * Created by moment on 2018/2/11.
 */

class RankListActivity : BaseActivity(), RankListContract.RankListView {
    private var mFragments = ArrayList<Fragment>()
    private var mTitles: ArrayList<HomeFragment.CategoryListEntity> = ArrayList()
    private var mTitle: ArrayList<String> = ArrayList()
    private var mAdapter: MyPagerAdapter? = null
    private var presenter: RankListContract.RankListPresenter? = null

    override fun getLayoutId(): Int = R.layout.ranklist_activity

    override fun initPresenter() {
        RankListPresenter(this)
    }

    override fun initView() {

        iv_back.setOnClickListener {
            onBackPressed()
        }
        tv_title.typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")

    }

    override fun onBackPressed() = finish()

    override fun initData() {
        presenter!!.rankList()
    }

    override fun setPresenter(presenter: RankListContract.RankListPresenter) {
        this.presenter = presenter
    }

    var tabInfo: RankList.TabInfo? = null

    override fun onRankListSucc(result: RankList) {
        val list = result.tabInfo!!.tabList
        tabInfo = result.tabInfo

        for (tab in list!!) {
            var category = HomeFragment.CategoryListEntity()
            category.category_id = tab.id.toString()
            category.name = tab.name.toString()
            mTitles.add(category)
            mTitle.add(tab.name.toString())
            var uri = Uri.parse(tab.apiUrl)
            var path: String = uri.path.toString().replace("/", File.separator)

            mFragments.add(RankListFragment(path, UriUtils().parseCategoriesTagListUri(tab.apiUrl!!).map))
        }
        mAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager.adapter = mAdapter
        val stringArray = mTitle.toArray(arrayOfNulls<String>(0))
        tab_layout.setViewPager(viewpager, stringArray as Array<String>)
        viewpager.offscreenPageLimit = list.size
        viewpager.currentItem = 0
    }

    override fun onRankListFail(error: Throwable?) = Unit

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int = mFragments.size

        override fun getPageTitle(position: Int): CharSequence = mTitle[position]

        override fun getItem(position: Int): Fragment {
            val list = tabInfo!!.tabList
            val tab = list!![position] as RankList.TabList
            var uri = Uri.parse(tab.apiUrl)
            var path: String = uri.path.toString().replace("/", File.separator)

            return RankListFragment(path, UriUtils().parseCategoriesTagListUri(tab.apiUrl!!).map)
        }

        override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE
    }
}