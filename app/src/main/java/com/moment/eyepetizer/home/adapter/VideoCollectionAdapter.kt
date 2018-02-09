package com.moment.eyepetizer.home.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.moment.eyepetizer.R
import com.moment.eyepetizer.utils.DensityUtil
import com.moment.eyepetizer.utils.ImageLoad


/**
 * Created by moment on 2018/2/5.
 */
class VideoCollectionAdapter(context: Context, private val mDatas: List<VideoCollection>) : RecyclerView.Adapter<VideoCollectionAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mContext: Context = context

    class VideoCollection {
        var icon: String? = null
        var title: String? = null
        var category: String? = null
    }

    inner class ViewHolder(arg0: View) : RecyclerView.ViewHolder(arg0) {

        internal var mImg: ImageView? = null
        internal var tv_title: TextView? = null
        internal var tv_content: TextView? = null
    }

    override fun getItemCount(): Int = mDatas.size

    /**
     * 创建ViewHolder
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.video_collection_recycler_item,
                viewGroup, false)
        val viewHolder = ViewHolder(view)

        viewHolder.mImg = view
                .findViewById(R.id.id_index_gallery_item_image) as ImageView
        viewHolder.tv_title = view.findViewById(R.id.tv_title) as TextView

        viewHolder.tv_content = view.findViewById(R.id.tv_content) as TextView
        return viewHolder
    }

    /**
     * 设置值
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 25f)
        var height = width * 0.6
        ImageLoad().load(mContext, mDatas.get(i).icon.toString(), viewHolder.mImg, width.toDouble().toInt(), height.toDouble().toInt(), 5)
        viewHolder.tv_title!!.text = mDatas.get(i).title
        viewHolder.tv_content!!.text = "#" + mDatas.get(i).category

    }

    private fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

}