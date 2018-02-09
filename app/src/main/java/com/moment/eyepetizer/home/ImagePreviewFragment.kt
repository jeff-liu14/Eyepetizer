package com.moment.eyepetizer.home

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.utils.DensityUtil
import com.moment.eyepetizer.utils.ImageLoad
import kotlinx.android.synthetic.main.image_preview_fragment.*

/**
 * Created by moment on 2018/2/5.
 */

class ImagePreviewFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.image_preview_fragment

    override fun initView() {
        var bundle: Bundle = arguments
        var url = bundle.getString("url")
        var width = getScreenWidth(activity) / 2 - DensityUtil.dip2px(activity, 20f)
        var height = width / 2
        ImageLoad().load(activity, url, image, width, height)
    }

    private fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    override fun initData() = Unit

//    companion object {
//
//        fun newInstance(imageUrl: String): ImagePreviewFragment {
//            val fragment = ImagePreviewFragment()
//            val bundle = Bundle()
//            bundle.putString("url", imageUrl)
//            fragment.arguments = bundle
//            return fragment
//        }
//    }

}