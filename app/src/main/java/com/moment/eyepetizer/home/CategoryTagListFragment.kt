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
import kotlinx.android.synthetic.main.category_taglist_fragment.*
import com.bumptech.glide.Glide
import com.moment.eyepetizer.home.adapter.MyMultiTypeAdapter
import com.moment.eyepetizer.home.mvp.CategoryTabListContract
import com.moment.eyepetizer.home.mvp.CategoryTabListPresenter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import kotlinx.android.synthetic.*


/**
 * Created by moment on 2018/2/2.
 */

class CategoryTagListFragment(id: String, path: String) : BaseFragment(), CategoryTabListContract.CategoriesTagListView {


    private var presenter: CategoryTabListContract.CategoriesTagListPresenter? = null
    var adapter: MyMultiTypeAdapter? = null
    private var isRefresh: Boolean = false
    private var category_id = id
    private var path: String? = path
    private var map: HashMap<String, String>? = HashMap()
    override fun getLayoutId(): Int = R.layout.category_taglist_fragment

    override fun initView() {

        Log.e("Fragment", "CategoryFragment initView()")
        swipeRefreshLayout.isEnableRefresh = false
        swipeRefreshLayout.refreshHeader = ClassicsHeader(activity) as RefreshHeader?
        swipeRefreshLayout.refreshFooter = ClassicsFooter(activity)

        swipeRefreshLayout.isEnableRefresh = false
        swipeRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            presenter!!.categoriesTagList(path.toString(), this!!.map!!)
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
        val layoutManager = LinearLayoutManager(activity.applicationContext)
        recyclerview.layoutManager = layoutManager
        adapter = MyMultiTypeAdapter(list, activity.applicationContext)
        recyclerview.adapter = adapter
        isRefresh = false
    }

    override fun initPresenter() {
        CategoryTabListPresenter(this)
    }

    override fun initData() {
        map!!.put("id", category_id)
        presenter!!.categoriesTagList(this!!.path!!, map!!)
    }

    override fun setPresenter(presenter: CategoryTabListContract.CategoriesTagListPresenter) {
        this.presenter = presenter
    }

    override fun onCategoriesTagSucc(t: Result) {
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

    override fun onCategoriesTagFail(error: Throwable?) {
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
        Log.e("Fragment", "CategoryFragment onDestroy() " + category_id)
        super.onDestroyView()
    }
}