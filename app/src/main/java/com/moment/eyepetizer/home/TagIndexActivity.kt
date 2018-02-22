package com.moment.eyepetizer.home

import android.graphics.Color
import android.net.Uri
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseActivity
import com.moment.eyepetizer.home.mvp.TagIndexContract
import com.moment.eyepetizer.home.mvp.TagIndexPresenter
import com.moment.eyepetizer.net.entity.TagIndex
import com.moment.eyepetizer.utils.AppBarStateChangeListener
import com.moment.eyepetizer.utils.ImageLoad
import com.moment.eyepetizer.utils.getScreenHeight
import com.moment.eyepetizer.utils.getScreenWidth
import kotlinx.android.synthetic.main.tagindex_activity.*
import java.io.File
import java.lang.ref.WeakReference

/**
 * Created by moment on 2018/2/11.
 */

class TagIndexActivity : BaseActivity(), TagIndexContract.TagIndexView {
    private var mFragments = ArrayList<Fragment>()
    private var mTitles: ArrayList<HomeFragment.CategoryListEntity> = ArrayList()
    private var mTitle: ArrayList<String> = ArrayList()
    private var mAdapter: MyPagerAdapter? = null
    private var presenter: TagIndexContract.TagIndexPresenter? = null
    private var id: Int? = 0
    private var title: String? = null

    override fun getLayoutId(): Int = R.layout.tagindex_activity

    override fun initPresenter() {
        TagIndexPresenter(this)
    }

    override fun initView() {
        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt("id")
            title = bundle.getString("title")
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
        presenter!!.tagIndex(this!!.id!!)
    }

    override fun setPresenter(presenter: TagIndexContract.TagIndexPresenter) {
        this.presenter = presenter
    }

    var tabInfo: TagIndex.TabInfo? = null

    override fun onTagIndexSucc(tagIndex: TagIndex) {
        val list = tagIndex.tabInfo!!.tabList
        tabInfo = tagIndex.tabInfo
        var tagInfo = tagIndex.tagInfo as TagIndex.TagInfo
        val width = getScreenWidth(applicationContext)
        val height = getScreenHeight(applicationContext) / 3
        ImageLoad().load(WeakReference(this@TagIndexActivity), tagInfo.headerImage.toString(), iv_bg, width, height)


        toolbar.title = ""

        tv_name.text = tagInfo.name.toString()
        var lp = ll_tag_index.layoutParams
        lp.width = width
        lp.height = height
        ll_tag_index.layoutParams = lp

        tv_description.text = "" + tagIndex.tagInfo!!.tagVideoCount + "作品 / " + tagIndex.tagInfo!!.tagFollowCount + "关注者 / " + tagIndex.tagInfo!!.tagDynamicCount + "动态"


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
    }

    override fun onTagIndexFail(throwable: Throwable) = Unit

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int = mFragments.size

        override fun getPageTitle(position: Int): CharSequence = mTitle[position]

        override fun getItem(position: Int): Fragment {
            val list = tabInfo!!.tabList
            val tab = list!![position]
            var uri = Uri.parse(tab.apiUrl)
            var path: String = uri.path.toString().replace("/", File.separator)

            var id = uri.getQueryParameter("id")
            return TagIndexFragment(id, path)
        }

        override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE
    }
}