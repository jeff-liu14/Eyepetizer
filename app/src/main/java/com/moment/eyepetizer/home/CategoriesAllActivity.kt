package com.moment.eyepetizer.home

import android.support.v7.widget.GridLayoutManager
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseActivity
import com.moment.eyepetizer.home.adapter.CategoriesAllAdapter
import com.moment.eyepetizer.home.mvp.CategoriesAllContract
import com.moment.eyepetizer.home.mvp.CategoriesAllPresenter
import com.moment.eyepetizer.net.entity.Result
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.categories_all_activity.*

/**
 * Created by moment on 2018/2/12.
 */

class CategoriesAllActivity : BaseActivity(), CategoriesAllContract.CategoriesAllView {

    internal var presenter: CategoriesAllContract.CategoriesAllPresenter? = null
    private var mAdapter: CategoriesAllAdapter? = null
    private var datas: ArrayList<Result.ItemList>? = null

    override fun getLayoutId(): Int = R.layout.categories_all_activity

    override fun initView() {
        iv_home.setOnClickListener {
            onBackPressed()
        }
        swipeRefreshLayout.refreshHeader = ClassicsHeader(this@CategoriesAllActivity)
        swipeRefreshLayout.setOnRefreshListener { initData() }
        val layoutManager = GridLayoutManager(this@CategoriesAllActivity, 2)
        recyclerview.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = getSpanSize(position, datas)
        }
        mAdapter = CategoriesAllAdapter(ArrayList(), this@CategoriesAllActivity)
        recyclerview.adapter = mAdapter
    }

    fun getSpanSize(position: Int, datas: ArrayList<Result.ItemList>?): Int {
        var data: Result.ItemList = datas!![position]
        var item: Map<String, Object> = data.data as Map<String, Object>
        var dataType = item["dataType"].toString()
        return if ("RectangleCard" == dataType) {
            2
        } else {
            1
        }

    }

    override fun onBackPressed() = finish()

    override fun initPresenter() {
        CategoriesAllPresenter(this)
    }

    override fun initData() {
        presenter!!.categoriesAll()
    }

    override fun setPresenter(presenter: CategoriesAllContract.CategoriesAllPresenter) {
        this.presenter = presenter
    }

    override fun onCategoriesAllSucc(result: Result) {
        datas = result.itemList as ArrayList<Result.ItemList>?
        val start = mAdapter!!.itemCount
        mAdapter!!.clearAll()
        mAdapter!!.notifyItemRangeRemoved(0, start)
        mAdapter!!.addAll(result.itemList as java.util.ArrayList<Result.ItemList>?)
        mAdapter!!.notifyItemRangeInserted(0, result.itemList!!.size)
        swipeRefreshLayout.finishRefresh()
    }

    override fun onCategoriesAllFail(error: Throwable) {
        swipeRefreshLayout.isLoadmoreFinished = false
        swipeRefreshLayout.finishRefresh()
    }
}