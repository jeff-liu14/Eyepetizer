package com.moment.eyepetizer.mine

import android.graphics.Typeface
import android.view.View
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.net.CallBack
import com.moment.eyepetizer.net.GetDataList
import com.moment.eyepetizer.net.entity.Result
import kotlinx.android.synthetic.main.mine_fragment.*

/**
 * Created by moment on 2018/2/2.
 */
class MineFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.mine_fragment
    }

    override fun initView() {
        tv_test.typeface = Typeface.createFromAsset(activity.assets, "fonts/Lobster-1.4.otf")
        tv_test!!.setOnClickListener {
            tv_test!!.text = System.currentTimeMillis().toString()
            GetDataList.discovery(object : CallBack<Result> {
                override fun onNext(t: Result) {
                    if (t != null) {
                        var data: Object? = t.itemList!![0].data
                        var jsons: String = JSONObject.toJSONString(data)
                        var obj: JSONObject = JSON.parseObject(jsons)
                        tv_test!!.text = jsons
                    }
                }

                override fun onError(e: Throwable) {
                    if (e != null) {
                        tv_test!!.text = e.message
                    }
                }

                override fun onCompleted() = Unit
            })
        }
    }

    override fun initData() {
    }

}