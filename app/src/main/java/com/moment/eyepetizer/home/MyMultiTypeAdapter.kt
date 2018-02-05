package com.moment.eyepetizer.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.moment.eyepetizer.R


import com.moment.eyepetizer.net.entity.Result
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.graphics.Bitmap
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SnapHelper
import android.text.TextUtils
import android.view.*
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.moment.eyepetizer.utils.DensityUtil
import com.moment.eyepetizer.utils.GlideRoundTransform
import com.moment.eyepetizer.utils.TimeUtils
import kotlin.collections.ArrayList


/**
 * Created by moment on 2017/12/11.
 */

class MyMultiTypeAdapter(datas: ArrayList<Result.ItemList>, var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var datas: ArrayList<Result.ItemList> = datas
    var mContext: Context = context

    enum class ITEM_TYPE(val type: String) {
        ITEM_TEXTCARD("textCard"),
        ITEM_BRIEFCARD("briefCard"),
        ITEM_DYNAMIC_INFOCARD("DynamicInfoCard"),
        ITEM_HORICONTAL_SCROLLCARD("horizontalScrollCard"),
        ITEM_FOLLOWCARD("followCard"),
        ITEM_VIDEOSMALLCARD("videoSmallCard")
    }


    fun clearAll() = this.datas.clear()

    fun removeAt(position: Int) {
        this.datas.removeAt(position)
    }

    fun addAll(data: ArrayList<Result.ItemList>?) {
        if (data == null) {
            return
        }
        this.datas.addAll(data)
    }

    fun getItem(postion: Int): Result.ItemList {
        return if (datas != null && postion <= datas.size) {
            datas[postion]
        } else Result.ItemList()
    }

    //创建新View，被LayoutManager所调用
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        if (viewType == ITEM_TYPE.ITEM_TEXTCARD.type.hashCode()) {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.text_card_item, viewGroup, false)
            return ItemTextCardItemHolder(view)
        } else if (viewType == ITEM_TYPE.ITEM_BRIEFCARD.type.hashCode()) {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.brief_card_item, viewGroup, false)
            return ItemBriefCardItemHolder(view)
        } else if (viewType == ITEM_TYPE.ITEM_DYNAMIC_INFOCARD.type.hashCode()) {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.dynamic_infocard_item, viewGroup, false)
            return ItemDynamicInfoCardItemHolder(view)
        } else if (viewType == ITEM_TYPE.ITEM_HORICONTAL_SCROLLCARD.type.hashCode()) {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.horizontal_scrollcard_item, viewGroup, false)
            return ItemHorizontalScrollCardHolder(view)
        } else if (viewType == ITEM_TYPE.ITEM_FOLLOWCARD.type.hashCode()) {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.followcard_item, viewGroup, false)
            return ItemFollowCardItemHolder(view)
        } else if (viewType == ITEM_TYPE.ITEM_VIDEOSMALLCARD.hashCode()) {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.video_smallcard_item, viewGroup, false)
            return ItemVideoSmallCardHolder(view)
        } else {
            var view = LayoutInflater.from(viewGroup.context).inflate(R.layout.text_card_item, viewGroup, false)
            return ItemEmptyHolder(view)
        }
        return null
    }

    private fun onItemTextCardBind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas.get(position)
        var holder: ItemTextCardItemHolder = viewHolder as ItemTextCardItemHolder
        var txtCard: Map<String, Object> = data.data as Map<String, Object>
//        var txtCard: TextCard = JSONObject.parseObject(data.data.toString(), TextCard::class.java)
        holder.tv_title!!.text = txtCard!!.get("text").toString()
    }

    private fun onItemBriefCardBind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas.get(position)
        var holder: ItemBriefCardItemHolder = viewHolder as ItemBriefCardItemHolder
        var briefCard: Map<String, Object> = data.data as Map<String, Object>
        holder.tv_title!!.text = briefCard.get("title").toString()
        holder.tv_content!!.text = briefCard.get("description").toString()
        Glide.with(mContext)
                .load(briefCard.get("icon").toString())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.image)


    }

    private fun onItemDynamicInfoCardBind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas.get(position)
        var holder: ItemDynamicInfoCardItemHolder = viewHolder as ItemDynamicInfoCardItemHolder
        var dynamicInfoCard: Map<String, Object> = data.data as Map<String, Object>
        holder.tv_print!!.text = dynamicInfoCard.get("text").toString()
        var user: Map<String, Object> = dynamicInfoCard.get("user") as Map<String, Object>

        Glide.with(mContext).load(user.get("avatar").toString()).asBitmap().centerCrop().into(object : BitmapImageViewTarget(holder.civ_icon) {
            override fun setResource(resource: Bitmap) {
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                circularBitmapDrawable.isCircular = true
                holder.civ_icon!!.setImageDrawable(circularBitmapDrawable)
            }
        })
        holder.tv_nickname!!.text = user.get("nickname").toString()
        var reply: Map<String, Object> = dynamicInfoCard.get("reply") as Map<String, Object>
        holder.tv_content!!.text = reply.get("message").toString()
        holder.tv_like!!.text = "赞" + reply.get("likeCount").toString().toFloat().toInt()


        var simpleVideo: Map<String, Object> = dynamicInfoCard.get("simpleVideo") as Map<String, Object>
        holder.tv_title!!.text = simpleVideo.get("title").toString()
        holder.tv_des!!.text = "#" + simpleVideo.get("category").toString()
        var cover: Map<String, Object> = simpleVideo.get("cover") as Map<String, Object>

        var width = getScreenWidth(mContext) * 2 / 5
        Glide.with(mContext)
                .load(cover.get("feed").toString())
                .override(width, width / 2)
                .into(holder.iv_conver)

        var obj: Double = simpleVideo.get("releaseTime").toString().toDouble()
        holder.tv_time!!.text = TimeUtils.getDiffTime(obj.toLong())
    }

    private fun onItemHorizontalScrollCardBind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas.get(position)
        var holder: ItemHorizontalScrollCardHolder = viewHolder as ItemHorizontalScrollCardHolder
        var horizontalScrollCard: Map<String, Object> = data.data as Map<String, Object>
        var itemList: List<Map<String, Object>> = horizontalScrollCard.get("itemList") as List<Map<String, Object>>
        var urlList: MutableList<String>? = ArrayList<String>()
        for (map in itemList) {
            var data: Map<String, Object> = map.get("data") as Map<String, Object>
            urlList!!.add(data.get("image").toString())
        }

        var linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerview!!.layoutManager = linearLayout
        var snapHelper: SnapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(holder.recyclerview)


        var adapter = GalleryAdapter(mContext, urlList!!.toList())
        holder.recyclerview!!.adapter = adapter

    }

    private fun onItemFollowCardBind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas.get(position)
        var holder: ItemFollowCardItemHolder = viewHolder as ItemFollowCardItemHolder
        var followCard: Map<String, Object> = data.data as Map<String, Object>
        var header: Map<String, Object> = followCard.get("header") as Map<String, Object>
        Glide.with(mContext).load(header.get("icon").toString()).asBitmap().centerCrop().into(object : BitmapImageViewTarget(holder.iv_icon) {
            override fun setResource(resource: Bitmap) {
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                circularBitmapDrawable.isCircular = true
                holder.iv_icon!!.setImageDrawable(circularBitmapDrawable)
            }
        })
        var list = TextUtils.split(header.get("description").toString(), "/")
        holder.tv_content!!.text = header.get("title").toString() + " / " + list.get(0)

        var content: Map<String, Object> = followCard.get("content") as Map<String, Object>
        var datas: Map<String, Object> = content.get("data") as Map<String, Object>
        var cover: Map<String, Object> = datas.get("cover") as Map<String, Object>
        var width = getScreenWidth(mContext)
        var height = width * 2 / 3
        Glide.with(mContext)
                .load(cover.get("feed").toString())
                .asBitmap()
                .transform(CenterCrop(mContext), GlideRoundTransform(mContext, 5))
                .override(width, height)
                .into(holder.iv_cover)

        Glide.with(mContext)
                .load(cover.get("blurred").toString())
                .asBitmap()
                .transform(CenterCrop(mContext), GlideRoundTransform(mContext, 5))
                .override(width, height)
                .into(holder.iv_cover_bg)


        holder.tv_time!!.text = TimeUtils.secToTime(datas.get("duration").toString().toFloat().toInt())

        holder.tv_title!!.text = datas.get("title").toString()
    }

    fun onItemVideoSmallCardBinder(viewHolder: RecyclerView.ViewHolder, position: Int) {

    }

    fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }


    //将数据与界面进行绑定的操作
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemTextCardItemHolder) {
            onItemTextCardBind(viewHolder, position)
        } else if (viewHolder is ItemBriefCardItemHolder) {
            onItemBriefCardBind(viewHolder, position)
        } else if (viewHolder is ItemDynamicInfoCardItemHolder) {
            onItemDynamicInfoCardBind(viewHolder, position)
        } else if (viewHolder is ItemHorizontalScrollCardHolder) {
            onItemHorizontalScrollCardBind(viewHolder, position)
        } else if (viewHolder is ItemFollowCardItemHolder) {
            onItemFollowCardBind(viewHolder, position)
        } else if (viewHolder is ItemVideoSmallCardHolder) {
            onItemVideoSmallCardBinder(viewHolder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        var type: String = datas!![position].type!!
        return type.hashCode()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    //获取数据的数量
    override fun getItemCount(): Int = datas.size

    inner class ItemTextCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_title: TextView? = null

        init {
            tv_title = itemView.findViewById(R.id.tv_title)
        }
    }

    inner class ItemBriefCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView? = null
        var tv_title: TextView? = null
        var tv_content: TextView? = null

        init {
            image = itemView.findViewById(R.id.image)
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_content = itemView.findViewById(R.id.tv_content)
        }
    }

    inner class ItemDynamicInfoCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var civ_icon: ImageView? = null
        var tv_nickname: TextView? = null
        var tv_print: TextView? = null
        var tv_content: TextView? = null
        var iv_conver: ImageView? = null
        var tv_title: TextView? = null
        var tv_des: TextView? = null
        var tv_like: TextView? = null
        var tv_time: TextView? = null

        init {
            civ_icon = itemView.findViewById(R.id.civ_icon)
            tv_nickname = itemView.findViewById(R.id.tv_nickname)
            tv_print = itemView.findViewById(R.id.tv_print)
            tv_content = itemView.findViewById(R.id.tv_content)
            iv_conver = itemView.findViewById(R.id.iv_conver)
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_des = itemView.findViewById(R.id.tv_des)
            tv_like = itemView.findViewById(R.id.tv_like)
            tv_time = itemView.findViewById(R.id.tv_time)
        }
    }

    inner class ItemHorizontalScrollCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recyclerview: RecyclerView? = null

        init {
            recyclerview = itemView.findViewById(R.id.recyclerview)
        }
    }

    inner class ItemFollowCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_cover: ImageView? = null
        var tv_time: TextView? = null
        var iv_icon: ImageView? = null
        var tv_title: TextView? = null
        var tv_content: TextView? = null
        var iv_cover_bg: ImageView? = null

        init {
            iv_cover = itemView.findViewById(R.id.iv_cover)
            tv_time = itemView.findViewById(R.id.tv_time)
            iv_icon = itemView.findViewById(R.id.iv_icon)
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_content = itemView.findViewById(R.id.tv_content)
            iv_cover_bg = itemView.findViewById(R.id.iv_cover_bg)
        }
    }

    inner class ItemVideoSmallCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_cover: ImageView? = null
        var tv_time: TextView? = null
        var iv_icon: ImageView? = null
        var tv_title: TextView? = null
        var tv_content: TextView? = null
        var iv_cover_bg: ImageView? = null

        init {
            iv_cover = itemView.findViewById(R.id.iv_cover)
            tv_time = itemView.findViewById(R.id.tv_time)
            iv_icon = itemView.findViewById(R.id.iv_icon)
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_content = itemView.findViewById(R.id.tv_content)
            iv_cover_bg = itemView.findViewById(R.id.iv_cover_bg)
        }
    }

    inner class ItemEmptyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        init {

        }
    }

}
