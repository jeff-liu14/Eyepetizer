package com.moment.eyepetizer.home

import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseActivity
import com.moment.eyepetizer.home.adapter.MyMultiTypeAdapter
import com.moment.eyepetizer.home.mvp.DiscussListContract
import com.moment.eyepetizer.home.mvp.DiscussListPresenter
import com.moment.eyepetizer.net.entity.Result
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.discuss_list_activity.*

/**
 * Created by moment on 2018/2/12.
 */

class DiscussListActivity : BaseActivity(), DiscussListContract.DiscussListView {

    private var presenter: DiscussListContract.DiscussListPresenter? = null
    private var isRefresh: Boolean = true
    var adapter: MyMultiTypeAdapter? = null
    var path: String? = null
    var map: HashMap<String, String> = HashMap()

    override fun getLayoutId(): Int = R.layout.discuss_list_activity

    override fun initPresenter() {
        DiscussListPresenter(this)
    }

    override fun initView() {
        var bundle = intent.extras
        var url = bundle.getString("url")
        var uri = Uri.parse(url)
        path = uri.path
        for (name in uri.queryParameterNames) {
            map.put(name, uri.getQueryParameter(name))
        }

        var title = bundle.getString("title")
        tv_bar_title.text = title
        iv_home.setOnClickListener {
            onBackPressed()
        }
        swipeRefreshLayout.refreshHeader = ClassicsHeader(applicationContext) as RefreshHeader?
        swipeRefreshLayout.refreshFooter = ClassicsFooter(applicationContext)
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            map.clear()
            map.put("page", "0")
            map.put("num", "10")
            presenter!!.getDiscussList(path.toString(), map)
        }

        swipeRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            presenter!!.getDiscussList(path.toString(), map)
        }

        val list = ArrayList<Result.ItemList>()
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = layoutManager
        adapter = MyMultiTypeAdapter(list, applicationContext)
        recyclerview.adapter = adapter
    }

    override fun onBackPressed() = finish()

    override fun initData() {
        presenter!!.getDiscussList(path.toString(), map)
    }

    override fun setPresenter(presenter: DiscussListContract.DiscussListPresenter) {
        this.presenter = presenter
    }

    override fun onDiscussListSucc(t: Result) {
        swipeRefreshLayout.finishLoadmore()
        swipeRefreshLayout.finishRefresh()
        swipeRefreshLayout.isLoadmoreFinished = TextUtils.isEmpty(t.nextPageUrl)
        if (!TextUtils.isEmpty(t.nextPageUrl)) {
            var uri = Uri.parse(t.nextPageUrl.toString())
            map.clear()
            for (name in uri.queryParameterNames) {
                map.put(name, uri.getQueryParameter(name))
            }
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

    override fun onDiscussListFail(throwable: Throwable?) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.isLoadmoreFinished = false
            swipeRefreshLayout.finishLoadmore()
        }
    }

}