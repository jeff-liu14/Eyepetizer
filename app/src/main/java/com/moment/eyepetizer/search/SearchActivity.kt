package com.moment.eyepetizer.search

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseActivity
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.search.mvp.SearchContract
import com.moment.eyepetizer.search.mvp.SearchHotPresenter
import com.moment.eyepetizer.view.FlowLayout
import com.moment.eyepetizer.view.SearchView
import kotlinx.android.synthetic.main.search_activity.*

/**
 * Created by moment on 2018/2/8.
 */

class SearchActivity : BaseActivity(), SearchContract.SearchHotView {

    lateinit var presenters: SearchContract.SearchHotPresenter
    internal var fragment: SearchFragment? = null
    internal var managers: FragmentManager? = null


    override fun getLayoutId(): Int = R.layout.search_activity

    override fun initPresenter() {
        SearchHotPresenter(this)
    }

    override fun initView() {
        search_view.addOnClearClickListener(object : SearchView.ClearButtonClick {
            override fun clear() {
                search_view.setText("")
                if (fragment != null) {
                    hideFragment(fragment)
                    fl_content.visibility = View.GONE
                    rl_search.visibility = View.VISIBLE
                }
            }

        })

        flow_layout.setOnFlowItemClickListener(object : FlowLayout.OnFlowItemClickListener {
            override fun onItemClick(txt: String) {
                search_view.setText(txt)
                search(txt)
            }
        })

        search_view.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val text = search_view.text.toString().trim { it <= ' ' }
                if (!TextUtils.isEmpty(text)) {
                    search(text)
                } else {
                    Toast.makeText(this@SearchActivity, "请输入搜索内容~", Toast.LENGTH_SHORT).show()
                }
                return@OnEditorActionListener true
            }
            false
        })

        tv_search_cancel.setOnClickListener {
            if (fragment != null) {
                hideFragment(fragment)
                fl_content.visibility = View.GONE
                rl_search.visibility = View.VISIBLE
            }
        }
    }

    private fun search(text: String) {
        fragment = SearchFragment.newInstance(text)
        addToActivity(R.id.fl_content, fragment)
        fl_content.visibility = View.VISIBLE
        rl_search.visibility = View.GONE
    }

    fun addToActivity(contentId: Int, fragment: BaseFragment?) {
        if (managers == null) {
            managers = supportFragmentManager
        }
        var transaction: FragmentTransaction? = managers!!.beginTransaction()
        if (transaction == null) {
            transaction = managers!!.beginTransaction()
        }
        if (fragment != null) {
            transaction!!.add(contentId, fragment)
            transaction.commitAllowingStateLoss()
        }
    }

    private fun hideFragment(fragment: Fragment?) {
        if (managers == null) {
            managers = supportFragmentManager
        }
        val transaction = managers!!.beginTransaction()
        if (fragment != null) {
            if (fragment.isAdded) {
                transaction.remove(fragment)
            }
        }
        if (fl_content.childCount > 0) {
            fl_content.removeAllViews()
        }
    }


    override fun initData() {
        presenters.hot()
    }

    override fun setPresenter(presenter: SearchContract.SearchHotPresenter) {
        this.presenters = presenter
    }

    override fun onHotSucc(tags: List<String>) = flow_layout.setTagView(this, tags.toList())

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top)
    }
}