package com.moment.eyepetizer.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import com.bumptech.glide.Glide
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.home.adapter.MyMultiTypeAdapter
import com.moment.eyepetizer.home.mvp.RecommendContract
import com.moment.eyepetizer.home.mvp.RecommendPresenter
import com.moment.eyepetizer.net.entity.Result
import com.moment.eyepetizer.utils.unbindDrawables
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.recommend_fragment.*

/**
 * Created by moment on 2018/2/5.
 */

class RecommendFragment : BaseFragment(), RecommendContract.RecommendView {
    private var presenter: RecommendContract.RecommendPresenter? = null
    var adapter: MyMultiTypeAdapter? = null
    var isRefresh: Boolean = false
    var page: Int = 0
    override fun getLayoutId(): Int = R.layout.recommend_fragment

    override fun initView() {
        swipeRefreshLayout.isEnableAutoLoadmore = true
        swipeRefreshLayout.refreshHeader = ClassicsHeader(activity) as RefreshHeader?
        swipeRefreshLayout.refreshFooter = ClassicsFooter(activity)
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            initData()
        }
        swipeRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            page++
            initData()
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

    override fun initData() {
        presenter!!.allRec(page)
    }

    override fun initPresenter() {
        RecommendPresenter(this)
    }

    override fun setPresenter(presenter: RecommendContract.RecommendPresenter) {
        this.presenter = presenter
    }

    override fun onRecommendSucc(t: Result) {
        swipeRefreshLayout.finishLoadmore()
        swipeRefreshLayout.finishRefresh()
        swipeRefreshLayout.isLoadmoreFinished = TextUtils.isEmpty(t.nextPageUrl)
        if (t.itemList!!.isEmpty()) return
        val start = adapter!!.itemCount
        if (isRefresh) {
            adapter!!.clearAll()
            adapter!!.notifyItemRangeRemoved(0, start)
            adapter!!.addAll(t.itemList as java.util.ArrayList<Result.ItemList>?)
            adapter!!.notifyItemRangeInserted(0, t.itemList!!.size)
        } else {
            adapter!!.addAll(t.itemList as java.util.ArrayList<Result.ItemList>)
            adapter!!.notifyItemRangeInserted(start, t.itemList!!.size)
        }
    }

    override fun onRecommendFail(error: Throwable) {
        swipeRefreshLayout.isLoadmoreFinished = false
        swipeRefreshLayout.finishLoadmore()
        swipeRefreshLayout.finishRefresh()
    }

    override fun onDestroyView() {
        Log.e("Fragment", "RecommendFragment onDestroy()")
        presenter = null
        recyclerview!!.adapter = null
        adapter = null
        recyclerview!!.addOnScrollListener(null)
        clearFindViewByIdCache()
        super.onDestroyView()
    }
}