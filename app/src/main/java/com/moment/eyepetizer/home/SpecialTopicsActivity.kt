package com.moment.eyepetizer.home

import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseActivity
import com.moment.eyepetizer.home.adapter.MyMultiTypeAdapter
import com.moment.eyepetizer.home.mvp.SpecialTopicsContract
import com.moment.eyepetizer.home.mvp.SpecialTopicsPresenter
import com.moment.eyepetizer.net.entity.Result
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.special_topics_activity.*

/**
 * Created by moment on 2018/2/12.
 */

class SpecialTopicsActivity : BaseActivity(), SpecialTopicsContract.SpecialTopicsView {

    private var presenter: SpecialTopicsContract.SpecialTopicsPresenter? = null
    private var isRefresh: Boolean = true
    var adapter: MyMultiTypeAdapter? = null
    private var start = 0
    private var num = 10

    override fun getLayoutId(): Int = R.layout.special_topics_activity

    override fun initPresenter() {
        SpecialTopicsPresenter(this)
    }

    override fun initView() {
        var title = intent.getStringExtra("title")
        tv_bar_title.text = title
        iv_home.setOnClickListener {
            onBackPressed()
        }
        swipeRefreshLayout.refreshHeader = ClassicsHeader(applicationContext) as RefreshHeader?
        swipeRefreshLayout.refreshFooter = ClassicsFooter(applicationContext)
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            start = 0
            num = 10
            presenter!!.specialTopics(start, num)
        }

        swipeRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            presenter!!.specialTopics(start, num)
        }

        val list = ArrayList<Result.ItemList>()
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = layoutManager
        adapter = MyMultiTypeAdapter(list, applicationContext)
        recyclerview.adapter = adapter
    }

    override fun onBackPressed() = finish()

    override fun initData() {
        presenter!!.specialTopics(start, num)
    }

    override fun setPresenter(presenter: SpecialTopicsContract.SpecialTopicsPresenter) {
        this.presenter = presenter
    }

    override fun onSpecialTopicsSucc(t: Result) {
        swipeRefreshLayout.finishLoadmore()
        swipeRefreshLayout.finishRefresh()
        swipeRefreshLayout.isLoadmoreFinished = TextUtils.isEmpty(t.nextPageUrl)
        if (!TextUtils.isEmpty(t.nextPageUrl)) {
            var uri = Uri.parse(t.nextPageUrl.toString())
            start = uri.getQueryParameter("start").toInt()
            num = uri.getQueryParameter("num").toInt()
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

    override fun onSpecialTopicsFail(throwable: Throwable?) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.isLoadmoreFinished = false
            swipeRefreshLayout.finishLoadmore()
        }
    }

}