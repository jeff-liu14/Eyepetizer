package com.moment.eyepetizer.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.moment.eyepetizer.R


/**
 * Created by moment on 2018/2/6.
 */

class ImageLoad {

    open fun load(context: Context, url: String, image: ImageView?) {
        if (image == null) return
        var requestOptions = RequestOptions().centerCrop()
                .placeholder(R.drawable.default_banner)
                .error(R.drawable.default_banner)
                .transform(CenterCrop())
                .format(DecodeFormat.PREFER_RGB_565)
                .priority(Priority.LOW)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)

        Glide.with(context.applicationContext)
                .load(url)
                .apply(requestOptions)
                .into(object : DrawableImageViewTarget(image) {
                })
    }

    open fun load(context: Context, url: String, image: ImageView?, width: Int, height: Int) {
        if (image == null) return
        var lp = image.layoutParams
        lp.width = width
        lp.height = height
        image.layoutParams = lp
        var requestOptions = RequestOptions().centerCrop()
                .placeholder(R.drawable.default_banner)
                .override(width, height)
                .format(DecodeFormat.PREFER_RGB_565)
                .error(R.drawable.default_banner)
                .transform(CenterCrop())
                .dontAnimate()
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
        Glide.with(context.applicationContext)
                .load(url)
                .apply(requestOptions)
                .into(object : DrawableImageViewTarget(image) {
                })
    }

    open fun loadCircle(context: Context, url: String, image: ImageView?) {
        if (image == null) return
        var lp = image.layoutParams
        lp.width = DensityUtil.dip2px(context, 40f)
        lp.height = DensityUtil.dip2px(context, 40f)
        image.layoutParams = lp
        var requestOptions = RequestOptions().centerCrop()
                .placeholder(R.drawable.default_icon)
                .error(R.drawable.default_icon)
                .format(DecodeFormat.PREFER_RGB_565)
                .transform(CenterCrop())
                .override(DensityUtil.dip2px(context, 40f))
                .dontAnimate()
                .priority(Priority.LOW)
                .transform(CircleCrop())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
        Glide.with(context.applicationContext)
                .load(url)
                .apply(requestOptions)
                .into(object : DrawableImageViewTarget(image) {
                })
    }

    open fun load(context: Context, url: String, image: ImageView?, width: Int, height: Int, round: Int) {
        if (image == null) return
        var lp = image.layoutParams
        lp.width = width
        lp.height = height
        image.layoutParams = lp
        var requestOptions = RequestOptions().centerCrop()
                .placeholder(R.drawable.default_banner)
                .error(R.drawable.default_banner)
                .format(DecodeFormat.PREFER_RGB_565)
                .dontAnimate()
                .override(width, height)
                .priority(Priority.LOW)
                .transforms(CenterCrop(), RoundedCorners(DensityUtil.dip2px(context, round.toFloat())))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
        Glide.with(context.applicationContext)
                .load(url)
                .apply(requestOptions)
                .into(object : DrawableImageViewTarget(image) {
                })
    }

    open fun clearCache(context: Context) = Thread(Runnable {
        Glide.get(context.applicationContext).clearDiskCache()
    }).start()

}