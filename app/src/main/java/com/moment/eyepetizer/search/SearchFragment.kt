package com.moment.eyepetizer.search

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.home.adapter.MyMultiTypeAdapter
import com.moment.eyepetizer.net.entity.Result
import com.moment.eyepetizer.search.mvp.SearchContract
import com.moment.eyepetizer.search.mvp.SearchPresenter
import com.moment.eyepetizer.utils.UriUtils
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.search_fragment.*

/**
 * Created by moment on 2018/2/5.
 */

class SearchFragment : BaseFragment(), SearchContract.SearchView {

    private lateinit var presenter: SearchContract.SearchPresenter
    var adapter: MyMultiTypeAdapter? = null
    var isRefresh: Boolean = false
    var start: Int = 0
    var num: Int = 10
    var search: String? = null
    override fun getLayoutId(): Int = R.layout.search_fragment

    override fun initView() {
        search = arguments.getString("search")
        swipeRefreshLayout.isEnableAutoLoadmore = true
        swipeRefreshLayout.refreshHeader = ClassicsHeader(activity) as RefreshHeader?
        swipeRefreshLayout.refreshFooter = ClassicsFooter(activity)
        swipeRefreshLayout.isEnableRefresh = false

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
        val list = ArrayList<Result.ItemList>()
        val layoutManager = LinearLayoutManager(this.activity!!)
        recyclerview.layoutManager = layoutManager
        adapter = MyMultiTypeAdapter(list, activity)
        recyclerview.adapter = adapter
        isRefresh = false
        ll_empty.visibility = View.GONE
        swipeRefreshLayout.visibility = View.VISIBLE

    }

    override fun initPresenter() {
        SearchPresenter(this)
    }

    override fun initData() {
        if (!TextUtils.isEmpty(search)) {
            presenter.search(start, num, this!!.search.toString())
        }
    }

    override fun setPresenter(presenter: SearchContract.SearchPresenter) {
        this.presenter = presenter
    }

    override fun onSearchSucc(t: Result) {
        if (!TextUtils.isEmpty(t.nextPageUrl)) {
            var parse: UriUtils.SearchParse = UriUtils().parseSearchUri(t.nextPageUrl.toString())
            start = parse.start
            num = parse.num
            search = parse.query
        }
        swipeRefreshLayout.finishLoadmore()
        swipeRefreshLayout.finishRefresh()
        swipeRefreshLayout.isLoadmoreFinished = TextUtils.isEmpty(t.nextPageUrl)
        if (t.itemList!!.isEmpty()) {
            ll_empty.visibility = View.VISIBLE
            swipeRefreshLayout.visibility = View.GONE
            return
        } else {
            ll_empty.visibility = View.GONE
            swipeRefreshLayout.visibility = View.VISIBLE
        }
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

    companion object {

        fun newInstance(index: String): SearchFragment {
            val fragment = SearchFragment()
            val bundle = Bundle()
            bundle.putString("search", index)
            fragment.arguments = bundle
            return fragment
        }
    }


}