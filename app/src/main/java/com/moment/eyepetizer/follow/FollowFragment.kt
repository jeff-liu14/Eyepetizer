package com.moment.eyepetizer.follow

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.follow.mvp.FollowContract
import com.moment.eyepetizer.follow.mvp.FollowPresenter
import com.moment.eyepetizer.home.adapter.MyMultiTypeAdapter
import com.moment.eyepetizer.net.entity.Result
import com.moment.eyepetizer.search.SearchActivity
import com.moment.eyepetizer.utils.UriUtils
import com.moment.eyepetizer.utils.unbindDrawables
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.follow_fragment.*

/**
 * Created by moment on 2018/2/2.
 */
class FollowFragment : BaseFragment(), FollowContract.FollowView {
    private var presenter: FollowContract.FollowPresenter? = null
    var adapter: MyMultiTypeAdapter? = null
    var isRefresh: Boolean = false
    var start_num: Int = 0
    var num: Int = 10
    var follow: Boolean = false
    var startId: Int = 0

    override fun getLayoutId(): Int = R.layout.follow_fragment

    override fun initView() {
        swipeRefreshLayout.refreshHeader = ClassicsHeader(activity) as RefreshHeader?
        swipeRefreshLayout.refreshFooter = ClassicsFooter(activity) as RefreshFooter?
        tv_bar_title.typeface = Typeface.createFromAsset(activity.assets, "fonts/Lobster-1.4.otf")
        swipeRefreshLayout.isEnableAutoLoadmore = true
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            start_num = 0
            num = 10
            follow = false
            startId = 0
            initData()
        }

        swipeRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
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

        iv_search.setOnClickListener {
            var intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_in_top)
        }

        val list = ArrayList<Result.ItemList>()
        val layoutManager = LinearLayoutManager(this.activity!!)
        recyclerview.layoutManager = layoutManager
        adapter = MyMultiTypeAdapter(list, activity)
        recyclerview.adapter = adapter
        isRefresh = false
    }

    override fun initPresenter() {
        FollowPresenter(this)
    }

    override fun initData() {
        presenter!!.follow(start_num, num, follow, startId)
    }

    override fun setPresenter(presenter: FollowContract.FollowPresenter) {
        this.presenter = presenter
    }

    override fun onFollowSucc(t: Result) {
        swipeRefreshLayout.finishLoadmore()
        swipeRefreshLayout.finishRefresh()
        swipeRefreshLayout.isLoadmoreFinished = t.itemList!!.isEmpty()
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

    override fun onError(error: Throwable?) {
        swipeRefreshLayout.isLoadmoreFinished = false
        swipeRefreshLayout.finishLoadmore()
        swipeRefreshLayout.finishRefresh()
    }

    override fun onDestroyView() {
        presenter = null
        recyclerview.adapter = null
        adapter = null
        recyclerview.addOnScrollListener(null)
        clearFindViewByIdCache()
        super.onDestroyView()
    }
}