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
import com.moment.eyepetizer.utils.TimeUtils
import java.lang.ref.WeakReference


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
        var duration: Long? = null
    }

    inner class ViewHolder(arg0: View) : RecyclerView.ViewHolder(arg0) {

        internal var mImg: ImageView? = null
        internal var tv_title: TextView? = null
        internal var tv_content: TextView? = null
        internal var tv_time: TextView? = null
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
                .findViewById(R.id.id_index_gallery_item_image)
        viewHolder.tv_title = view.findViewById(R.id.tv_title)

        viewHolder.tv_content = view.findViewById(R.id.tv_content)
        viewHolder.tv_time = view.findViewById(R.id.tv_time)
        return viewHolder
    }

    /**
     * 设置值
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 25f)
        var height = width * 0.6
        ImageLoad().load(mDatas.get(i).icon.toString(), viewHolder.mImg, width.toDouble().toInt(), height.toDouble().toInt(), 5)
        viewHolder.tv_title!!.text = mDatas.get(i).title
        viewHolder.tv_content!!.text = "#" + mDatas.get(i).category
        viewHolder.tv_time!!.text = TimeUtils.secToTime(mDatas.get(i).duration!!.toInt())

    }

    private fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

}