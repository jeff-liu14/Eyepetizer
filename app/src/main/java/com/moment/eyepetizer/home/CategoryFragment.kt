package com.moment.eyepetizer.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.event.RxBus
import com.moment.eyepetizer.event.entity.CurrentTagEvent
import com.moment.eyepetizer.net.entity.Result
import com.moment.eyepetizer.utils.UriUtils
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.category_fragment.*
import com.bumptech.glide.Glide
import com.moment.eyepetizer.home.adapter.MyMultiTypeAdapter
import com.moment.eyepetizer.home.mvp.CategoryContract
import com.moment.eyepetizer.home.mvp.CategoryPresenter
import kotlinx.android.synthetic.*


/**
 * Created by moment on 2018/2/2.
 */

class CategoryFragment(id: String) : BaseFragment(), CategoryContract.CategoryView {

    private var presenter: CategoryContract.CategoryPresenter? = null
    var adapter: MyMultiTypeAdapter? = null
    var isRefresh: Boolean = false
    var category_id = id
    var start_num: Int = 0
    var num: Int = 10
    override fun getLayoutId(): Int = R.layout.category_fragment

    override fun initEvent() {
        RxBus.default!!.toObservable(CurrentTagEvent::class.java)
                .subscribe { currentTagEvent ->
                    if (currentTagEvent.tag != null && currentTagEvent.tag.equals(category_id)) {
                        if (currentTagEvent.isForceRefresh) {
                            isRefresh = true
                            swipeRefreshLayout.autoRefresh(0)
                        } else {
                            if (adapter != null && adapter!!.itemCount == 0) {
                                presenter!!.category(category_id.toInt(), start_num, num)
                            }
                        }
                    }
                }
    }

    override fun initView() {
        Log.e("Fragment", "CategoryFragment initView()")
        swipeRefreshLayout.isEnableAutoLoadmore = true
        swipeRefreshLayout.refreshHeader = ClassicsHeader(activity)
        swipeRefreshLayout.refreshFooter = ClassicsFooter(activity)
//        swipeRefreshLayout.setOnRefreshListener {
//            isRefresh = true
//            presenter!!.category(category_id.toInt(), start_num, num)
//        }
        swipeRefreshLayout.isEnableRefresh = false

        swipeRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            presenter!!.category(category_id.toInt(), start_num, num)
        }

        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //0 表示停止滑动的状态 SCROLL_STATE_IDLE
                //1表示正在滚动，用户手指在屏幕上 SCROLL_STATE_TOUCH_SCROLL
                //2表示正在滑动。用户手指已经离开屏幕 SCROLL_STATE_FLING
                when (newState) {
                    2 -> {
                        Glide.with(activity.applicationContext).pauseRequests()
                    }
                    0 -> {
                        Glide.with(activity.applicationContext).resumeRequests()
                    }
                    1 -> {
                        Glide.with(activity.applicationContext).resumeRequests()
                    }
                }

            }
        })


        val list = ArrayList<Result.ItemList>()
        val layoutManager = LinearLayoutManager(this.activity!!)
        recyclerview.layoutManager = layoutManager
        adapter = MyMultiTypeAdapter(list, activity)
        recyclerview.adapter = adapter
        isRefresh = false
    }

    override fun initPresenter() {
        CategoryPresenter(this)
    }

    override fun initData() = Unit

    override fun setPresenter(presenter: CategoryContract.CategoryPresenter) {
        this.presenter = presenter
    }

    override fun onCategorySucc(t: Result) {
        swipeRefreshLayout.finishLoadmore()
        swipeRefreshLayout.finishRefresh()
        swipeRefreshLayout.isLoadmoreFinished = TextUtils.isEmpty(t.nextPageUrl)
        if (!TextUtils.isEmpty(t.nextPageUrl)) {
            start_num = UriUtils().parseCategoryUri(t.nextPageUrl.toString()).start
            num = UriUtils().parseCategoryUri(t.nextPageUrl.toString()).num
        }
        if (t.itemList!!.isEmpty()) return
        val start = adapter!!.itemCount
        if (isRefresh) {
            adapter!!.clearAll()
            adapter!!.notifyItemRangeRemoved(0, start)
            adapter!!.addAll(t.itemList as java.util.ArrayList<Result.ItemList>?)
            adapter!!.notifyItemRangeInserted(0, t.itemList!!.size)
            start_num = 0
            num = 10
        } else {
            adapter!!.addAll(t.itemList as java.util.ArrayList<Result.ItemList>)
            adapter!!.notifyItemRangeInserted(start, t.itemList!!.size)
        }
    }

    override fun onCategoryFail(error: Throwable) {
        swipeRefreshLayout.isLoadmoreFinished = false
        swipeRefreshLayout.finishLoadmore()
        swipeRefreshLayout.finishRefresh()
    }

    override fun onDestroyView() {
        recyclerview!!.adapter = null
        adapter = null
        presenter = null
        recyclerview!!.addOnScrollListener(null)
        clearFindViewByIdCache()
        Log.e("Fragment", "CategoryFragment onDestroy() " + category_id)
        super.onDestroyView()
    }
}