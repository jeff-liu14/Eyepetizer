package com.moment.eyepetizer.home

import android.graphics.Color
import android.net.Uri
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.Log
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseActivity
import com.moment.eyepetizer.home.mvp.CategoriesDetailContract
import com.moment.eyepetizer.home.mvp.CategoriesDetailPresenter
import com.moment.eyepetizer.net.entity.CategoryInfo
import com.moment.eyepetizer.utils.AppBarStateChangeListener
import com.moment.eyepetizer.utils.ImageLoad
import com.moment.eyepetizer.utils.getScreenHeight
import com.moment.eyepetizer.utils.getScreenWidth
import kotlinx.android.synthetic.main.categories_taglist_activity.*
import java.io.File

/**
 * Created by moment on 2018/2/11.
 */

class CategoriesTagListActivity : BaseActivity(), CategoriesDetailContract.CategoriesDetailView {
    private var mFragments = ArrayList<Fragment>()
    private var mTitles: ArrayList<HomeFragment.CategoryListEntity> = ArrayList()
    private var mTitle: ArrayList<String> = ArrayList()
    private var mAdapter: MyPagerAdapter? = null
    private var presenter: CategoriesDetailContract.CategoriesDetailPresenter? = null
    private var id: Int? = 0
    private var title: String? = null
    private var tabIndex: Int = 0

    override fun getLayoutId(): Int = R.layout.categories_taglist_activity

    override fun initPresenter() {
        CategoriesDetailPresenter(this)
    }

    override fun initView() {
        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt("id")
            title = bundle.getString("title")
            tabIndex = bundle.getInt("tabIndex", 0)
        }

        appbarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) =
                    when (state) {
                        State.EXPANDED -> {

                            //展开状态
                            toolbar.setNavigationIcon(R.drawable.ic_action_back_white)
                            tv_title.setTextColor(Color.WHITE)
                            tv_title.text = ""
                        }
                        State.COLLAPSED -> {

                            //折叠状态
                            toolbar.setNavigationIcon(R.drawable.ic_action_back)
                            tv_title.setTextColor(Color.BLACK)
                            tv_title.text = title

                        }
                        else -> {

                            //中间状态
                            toolbar.setNavigationIcon(R.drawable.ic_action_back_white)
                            tv_title.setTextColor(Color.WHITE)
                            tv_title.text = ""
                        }
                    }

        })
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() = finish()

    override fun initData() {
        presenter!!.categoriesDetail(this!!.id!!)
    }

    override fun setPresenter(presenter: CategoriesDetailContract.CategoriesDetailPresenter) {
        this.presenter = presenter
    }

    var tabInfo: CategoryInfo.TabInfo? = null
    override fun onCategoriesDetailSucc(result: CategoryInfo) {
        val list = result.tabInfo!!.tabList
        tabInfo = result.tabInfo
        var categoryInfo = result.categoryInfo as CategoryInfo.CategoryInfo
        val width = getScreenWidth(applicationContext)
        val height = getScreenHeight(applicationContext) / 3
        ImageLoad().load(categoryInfo.headerImage.toString(), iv_bg, width, height)


        toolbar.title = ""

        tv_name.text = categoryInfo.name.toString()
        tv_description.text = categoryInfo.description.toString()

        for (tab in list!!) {
            var category = HomeFragment.CategoryListEntity()
            category.category_id = tab.id.toString()
            category.name = tab.name.toString()
            mTitles.add(category)
            mTitle.add(tab.name.toString())
            var uri = Uri.parse(tab.apiUrl)
            var path: String = uri.path.toString().replace("/", File.separator)

            var id = uri.getQueryParameter("id")
            mFragments.add(CategoryTagListFragment(id, path))
        }
        mAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager.adapter = mAdapter
        val stringArray = mTitle.toArray(arrayOfNulls<String>(0))
        tab_layout.setViewPager(viewpager, stringArray as Array<String>)
        viewpager.offscreenPageLimit = list.size
        viewpager.currentItem = 0

        if (tabIndex < tab_layout.tabCount) {
            tab_layout.setCurrentTab(tabIndex, true)
        }
    }

    override fun onCategoriesDetailFail(error: Throwable?) {
    }

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int = mFragments.size

        override fun getPageTitle(position: Int): CharSequence = mTitle[position]

        override fun getItem(position: Int): Fragment {
            val list = tabInfo!!.tabList
            val tab = list!![position] as CategoryInfo.TabList
            var uri = Uri.parse(tab.apiUrl)
            var path: String = uri.path.toString().replace("/", File.separator)

            var id = uri.getQueryParameter("id")
            return CategoryTagListFragment(id, path)
        }

        override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE
    }
}