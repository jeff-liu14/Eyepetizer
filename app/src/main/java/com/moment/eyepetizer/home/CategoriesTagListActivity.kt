package com.moment.eyepetizer.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.DragEvent
import android.view.View
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
import java.lang.ref.WeakReference

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

    override fun getLayoutId(): Int = R.layout.categories_taglist_activity

    override fun initPresenter() {
        CategoriesDetailPresenter(this)
    }

    var verticalOffsetLast: Int = 0

    override fun initView() {
        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt("id")
        }

        appbarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) =
                    when (state) {
                        State.EXPANDED -> {

                            //展开状态
                            toolbar.setNavigationIcon(R.drawable.btn_back)
                            toolbar.setTitleTextColor(Color.WHITE)
                        }
                        State.COLLAPSED -> {

                            //折叠状态
                            var drawable = resources.getDrawable(R.drawable.btn_back)
//                            DrawableCompat.setTintList(drawable, ColorStateList.valueOf(Color.BLACK))
                            toolbar.setNavigationIcon(drawable)
                            toolbar.setTitleTextColor(Color.BLACK)

                        }
                        else -> {

                            //中间状态

                        }
                    }

        })
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
        ImageLoad().load(WeakReference(this@CategoriesTagListActivity), categoryInfo.headerImage.toString(), iv_bg, width, height)


        title = categoryInfo.name.toString()
        toolbar.title = title

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
            mFragments.add(CategoryTabListFragment(id, path))
        }
        mAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager.adapter = mAdapter
        val stringArray = mTitle.toArray(arrayOfNulls<String>(0))
        tab_layout.setViewPager(viewpager, stringArray)
        viewpager.offscreenPageLimit = list.size
        viewpager.currentItem = 0
    }

    override fun onCategoriesDetailFail(error: Throwable) {
        Log.d("error", error.localizedMessage)
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
            return CategoryTabListFragment(id, path)
        }

        override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE
    }
}