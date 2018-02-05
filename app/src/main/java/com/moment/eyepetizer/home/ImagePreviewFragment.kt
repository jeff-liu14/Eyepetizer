package com.moment.eyepetizer.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.moment.eyepetizer.utils.DensityUtil
import kotlinx.android.synthetic.main.image_preview_fragment.*

/**
 * Created by moment on 2018/2/5.
 */

class ImagePreviewFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.image_preview_fragment
    }

    override fun initView() {
        var bundle: Bundle = arguments
        var url = bundle.getString("url")
        var width = getScreenWidth(activity) / 2 - DensityUtil.dip2px(activity, 20f)
        var height = width / 2
        Glide.with(activity)
                .load(url)
                .asBitmap()
                .override(width, height)
                .into(object : BitmapImageViewTarget(image) {
                    override fun setResource(resource: Bitmap?) {
                        val roundBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                        roundBitmapDrawable.cornerRadius = DensityUtil.dip2px(activity, 5f).toFloat()
                        image!!.setImageDrawable(roundBitmapDrawable)
                    }

                })
    }

    fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    override fun initData() {
    }

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