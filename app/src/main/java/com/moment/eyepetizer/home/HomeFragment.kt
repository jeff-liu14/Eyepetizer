package com.moment.eyepetizer.home

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.Toast
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import kotlinx.android.synthetic.main.home_fragment.*
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import com.moment.eyepetizer.event.RxBus
import com.moment.eyepetizer.event.entity.CurrentTagEvent
import com.moment.eyepetizer.event.entity.RefreshEvent
import com.moment.eyepetizer.home.mvp.CategoriesContract
import com.moment.eyepetizer.home.mvp.CategoriesPresenter
import com.moment.eyepetizer.net.entity.Categories
import com.moment.eyepetizer.search.SearchActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by moment on 2018/2/2.
 */
class HomeFragment : BaseFragment(), ViewPager.OnPageChangeListener, CategoriesContract.CategoriesView {
    private lateinit var presenter: CategoriesContract.CategoriesPresenter
    private var currentIndex = ""

    private var mFragments = ArrayList<Fragment>()
    var str = arrayOf("发现", "推荐", "日报")
    override fun getLayoutId(): Int = R.layout.home_fragment

    var tabId: Long = 10001

    override fun initView() {
        iv_home.setColorFilter(resources.getColor(R.color.black))
        tv_bar_title.visibility = View.GONE
        tab_layout.visibility = View.VISIBLE
        iv_home.setOnClickListener { Toast.makeText(activity, "home", Toast.LENGTH_SHORT).show() }
        iv_search.setOnClickListener {
            var intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_in_top)
        }
    }

    override fun initEvent() {
        RxBus.default!!.toObservable(RefreshEvent::class.java)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Toast.makeText(activity, "refresh", Toast.LENGTH_SHORT).show() }
    }

    override fun initPresenter() {
        CategoriesPresenter(this)
    }

    class CategoryListEntity {

        var category_id: String? = null
        var name: String? = null
    }

    private var mTitles: ArrayList<CategoryListEntity> = ArrayList()
    private var mTitle: ArrayList<String> = ArrayList()
    private var mAdapter: MyPagerAdapter? = null
    override fun initData() {
        presenter.categories()
    }

    override fun setPresenter(presenter: CategoriesContract.CategoriesPresenter) {
        this.presenter = presenter
    }

    override fun onCategoriesSucc(t: List<Categories>) {
        var list = t.toMutableList()

        var tabs: MutableList<Categories> = ArrayList<Categories>() as MutableList<Categories>
        for (tab in str) {
            val cate = Categories()
            cate.name = tab
            cate.id = tabId
            tabId++
            tabs.add(cate)
        }

        list.addAll(0, tabs)
        for (cate in list) {
            when {
                cate.id == 10001L -> mFragments.add(DiscoveryFragment())
                cate.id == 10002L -> mFragments.add(RecommendFragment())
                cate.id == 10003L -> mFragments.add(FeedFragment())
                else -> mFragments.add(CategoryFragment(cate.id.toString()))
            }
            var category = CategoryListEntity()
            category.category_id = cate.id.toString()
            category.name = cate.name
            mTitles.add(category)
            mTitle.add(cate.name.toString())
        }
        mAdapter = MyPagerAdapter(fragmentManager)
        viewpager.adapter = mAdapter
        val stringArray = mTitle.toArray(arrayOfNulls<String>(0))
        tab_layout.setViewPager(viewpager, stringArray)
        viewpager.offscreenPageLimit = mTitles.size
        viewpager.currentItem = 0
        viewpager.addOnPageChangeListener(this)
    }

    override fun onCategoriesFail(error: Throwable) =
            Toast.makeText(activity, error.message + "", Toast.LENGTH_SHORT).show()

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int = mFragments.size

        override fun getPageTitle(position: Int): CharSequence = mTitle[position]

        override fun getItem(position: Int): Fragment = mFragments[position]

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) =
                //            super.destroyItem(container, position, `object`)
                Unit
    }

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) =
            Unit

    override fun onPageSelected(position: Int) {
        if (mAdapter != null && mTitles != null && mTitles!!.size - 1 >= position) {
            val entity = mTitles!!.get(position)
            currentIndex = entity.category_id.toString()
            RxBus.default!!.post(CurrentTagEvent(currentIndex, false))
        }
    }
}