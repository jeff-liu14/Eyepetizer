package com.moment.eyepetizer.home

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.ViewGroup
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.moment.eyepetizer.R
import com.moment.eyepetizer.utils.DensityUtil


/**
 * Created by moment on 2018/2/5.
 */
class GalleryAdapter(context: Context, private val mDatas: List<String>) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater
    private var mContext: Context = context

    init {
        mInflater = LayoutInflater.from(context)
    }

    inner class ViewHolder(arg0: View) : RecyclerView.ViewHolder(arg0) {

        internal var mImg: ImageView? = null
        internal var mTxt: TextView? = null
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    /**
     * 创建ViewHolder
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.activity_recycler_item,
                viewGroup, false)
        val viewHolder = ViewHolder(view)

        viewHolder.mImg = view
                .findViewById(R.id.id_index_gallery_item_image) as ImageView
        return viewHolder
    }

    /**
     * 设置值
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        var width = getScreenWidth(mContext)
        var height = width / 2
        Glide.with(mContext)
                .load(mDatas.get(i))
                .asBitmap()
                .override(width, height)
                .into(object : BitmapImageViewTarget(viewHolder.mImg) {
                    override fun setResource(resource: Bitmap?) {
                        val roundBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.resources, resource)
                        roundBitmapDrawable.cornerRadius = DensityUtil.dip2px(mContext, 5f).toFloat()
                        viewHolder.mImg!!.setImageDrawable(roundBitmapDrawable)
                    }

                })
    }

    fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

}