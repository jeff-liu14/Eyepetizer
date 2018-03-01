package com.moment.eyepetizer.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.net.entity.Result
import com.moment.eyepetizer.utils.UriUtils
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.ranklist_fragment.*
import com.bumptech.glide.Glide
import com.moment.eyepetizer.home.adapter.MyMultiTypeAdapter
import com.moment.eyepetizer.home.mvp.RankListDetailContract
import com.moment.eyepetizer.home.mvp.RankListDetailPresenter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import kotlinx.android.synthetic.*


/**
 * Created by moment on 2018/2/2.
 */

class RankListFragment(path: String, map: HashMap<String, String>?) : BaseFragment(), RankListDetailContract.RankListView {
    private var presenter: RankListDetailContract.RankListPresenter? = null
    var adapter: MyMultiTypeAdapter? = null
    private var isRefresh: Boolean = false
    private var path: String? = path
    private var map: HashMap<String, String>? = map
    override fun getLayoutId(): Int = R.layout.ranklist_fragment

    override fun initView() {

        Log.e("Fragment", "CategoryFragment initView()")
        swipeRefreshLayout.isEnableRefresh = false
        swipeRefreshLayout.refreshHeader = ClassicsHeader(activity) as RefreshHeader?
        swipeRefreshLayout.refreshFooter = ClassicsFooter(activity)

        swipeRefreshLayout.isEnableRefresh = false
        swipeRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            presenter!!.rankListVideo(path.toString(), this!!.map!!)
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
        RankListDetailPresenter(this)
    }

    override fun initData() {
        presenter!!.rankListVideo(this!!.path!!, map!!)
    }

    override fun setPresenter(presenter: RankListDetailContract.RankListPresenter) {
        this.presenter = presenter
    }

    override fun onRankListSucc(t: Result) {
        swipeRefreshLayout.finishLoadmore()
        swipeRefreshLayout.finishRefresh()
        swipeRefreshLayout.isLoadmoreFinished = TextUtils.isEmpty(t.nextPageUrl)
        if (!TextUtils.isEmpty(t.nextPageUrl)) {
            var categoriesTagListParse = UriUtils().parseCategoriesTagListUri(t.nextPageUrl.toString())
            map!!.clear()
            map = categoriesTagListParse.map
        }
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

    override fun onRankListFail(error: Throwable?) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.isLoadmoreFinished = false
            swipeRefreshLayout.finishLoadmore()
        }
    }

    override fun onDestroyView() {
        recyclerview!!.adapter = null
        adapter = null
        presenter = null
        recyclerview!!.addOnScrollListener(null)
        clearFindViewByIdCache()
        super.onDestroyView()
    }
}