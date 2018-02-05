package com.moment.eyepetizer.home

import android.support.v7.widget.LinearLayoutManager
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import kotlinx.android.synthetic.main.discovery_fragment.*

/**
 * Created by moment on 2018/2/5.
 */

class DiscoveryFragment : BaseFragment() {
    var adapter: MyMultiTypeAdapter? = null
    var isRefresh: Boolean = false
    override fun getLayoutId(): Int {
        return R.layout.discovery_fragment
    }

    override fun initView() {
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            initData()
        }
        val list = ArrayList<Result.ItemList>()
        val layoutManager = LinearLayoutManager(this.activity!!)
        recyclerview.layoutManager = layoutManager
        adapter = MyMultiTypeAdapter(list, activity)
        recyclerview.adapter = adapter
        isRefresh = false
    }

    override fun initData() {
        GetDataList.discovery(object : CallBack<Result> {
            override fun onCompleted() = Unit

            override fun onError(e: Throwable) {
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onNext(t: Result) {
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
                swipeRefreshLayout.isRefreshing = false
            }

        })
    }


}