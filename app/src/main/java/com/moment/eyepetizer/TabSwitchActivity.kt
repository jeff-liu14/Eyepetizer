package com.moment.eyepetizer

import android.support.v7.widget.LinearLayoutManager
import com.moment.eyepetizer.base.BaseActivity
import com.moment.eyepetizer.home.mvp.CategoriesContract
import com.moment.eyepetizer.home.mvp.CategoriesPresenter
import com.moment.eyepetizer.net.entity.Categories
import android.support.v7.widget.helper.ItemTouchHelper
import com.moment.eyepetizer.utils.ItemTouchHelperCallback
import com.moment.eyepetizer.utils.TabSwitchAdapter
import kotlinx.android.synthetic.main.tab_switch_activity.*
import java.util.*


/**
 * Created by moment on 2018/3/7.
 */

class TabSwitchActivity : BaseActivity(), CategoriesContract.CategoriesView {
    private var presenter: CategoriesContract.CategoriesPresenter? = null
    var adapter: TabSwitchAdapter? = null

    override fun getLayoutId(): Int = R.layout.tab_switch_activity

    override fun initView() {
        back.setOnClickListener {
            finish()
        }
        val list = ArrayList<Categories>()
        val layoutManager = LinearLayoutManager(this.applicationContext)
        recyclerview.layoutManager = layoutManager
        adapter = TabSwitchAdapter(list)
        recyclerview.adapter = adapter
        var itemTouchHelperCallback = ItemTouchHelperCallback(adapter!!)
        var itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview)
    }

    override fun initData() {
        presenter?.categories()
    }

    override fun initPresenter() {
        CategoriesPresenter(this)
    }

    override fun setPresenter(presenter: CategoriesContract.CategoriesPresenter) {
        this.presenter = presenter
    }

    override fun onCategoriesSucc(result: List<Categories>) {
        adapter?.clearAll()
        adapter?.addAll(result as ArrayList<Categories>)
        adapter?.notifyDataSetChanged()
    }

    override fun onCategoriesFail(error: Throwable?) {

    }


}