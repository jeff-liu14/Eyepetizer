package com.moment.eyepetizer.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.moment.eyepetizer.R


import com.moment.eyepetizer.net.entity.Result
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SnapHelper
import android.text.TextUtils
import android.view.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.moment.eyepetizer.utils.DensityUtil
import com.moment.eyepetizer.utils.ImageLoad
import com.moment.eyepetizer.utils.TimeUtils
import kotlinx.android.synthetic.main.banner_item.view.*
import kotlinx.android.synthetic.main.brief_card_item.view.*
import kotlinx.android.synthetic.main.text_card_item.view.*
import kotlinx.android.synthetic.main.dynamic_infocard_item.view.*
import kotlinx.android.synthetic.main.horizontal_scrollcard_item.view.*
import kotlinx.android.synthetic.main.followcard_item.view.*
import kotlinx.android.synthetic.main.video_smallcard_item.view.*
import kotlinx.android.synthetic.main.squarecard_collection_item.view.*
import kotlinx.android.synthetic.main.videocollection_withbrief_item.view.*
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
        ITEM_VIDEOSMALLCARD("videoSmallCard"),
        ITEM_SQUARECARD_COLLECTION("squareCardCollection"),
        ITEM_VIDEOCOLLECTION_WITHBRIEF("videoCollectionWithBrief"),
        ITEM_BANNER("banner"),
        ITEM_VIDEO("video")
    }


    fun clearAll() = this.datas.clear()

    fun addAll(data: ArrayList<Result.ItemList>?) {
        if (data == null) {
            return
        }
        this.datas.addAll(data)
    }

    //创建新View，被LayoutManager所调用
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            ITEM_TYPE.ITEM_TEXTCARD.type.hashCode() -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.text_card_item, viewGroup, false)
                return ItemTextCardItemHolder(view)
            }
            ITEM_TYPE.ITEM_BRIEFCARD.type.hashCode() -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.brief_card_item, viewGroup, false)
                return ItemBriefCardItemHolder(view)
            }
            ITEM_TYPE.ITEM_DYNAMIC_INFOCARD.type.hashCode() -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.dynamic_infocard_item, viewGroup, false)
                return ItemDynamicInfoCardItemHolder(view)
            }
            ITEM_TYPE.ITEM_HORICONTAL_SCROLLCARD.type.hashCode() -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.horizontal_scrollcard_item, viewGroup, false)
                return ItemHorizontalScrollCardHolder(view)
            }
            ITEM_TYPE.ITEM_FOLLOWCARD.type.hashCode() -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.followcard_item, viewGroup, false)
                return ItemFollowCardItemHolder(view)
            }
            ITEM_TYPE.ITEM_VIDEOSMALLCARD.type.hashCode() -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.video_smallcard_item, viewGroup, false)
                return ItemVideoSmallCardHolder(view)
            }
            ITEM_TYPE.ITEM_SQUARECARD_COLLECTION.type.hashCode() -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.squarecard_collection_item, viewGroup, false)
                return ItemSquareCardCollectionHolder(view)
            }
            ITEM_TYPE.ITEM_VIDEOCOLLECTION_WITHBRIEF.type.hashCode() -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.videocollection_withbrief_item, viewGroup, false)
                return ItemVideoCollectionWithBriefHolder(view)
            }
            ITEM_TYPE.ITEM_BANNER.type.hashCode() -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.banner_item, viewGroup, false)
                return ItemBannerHolder(view)
            }
            ITEM_TYPE.ITEM_VIDEO.type.hashCode() -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.followcard_item, viewGroup, false)
                return ItemVideoItemHolder(view)
            }
            else -> {
                var view = LayoutInflater.from(viewGroup.context).inflate(R.layout.text_card_item, viewGroup, false)
                view.visibility = View.GONE
                return ItemEmptyHolder(view)
            }
        }
        return null
    }

    private fun onItemTextCardBind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas[position]
        var holder: ItemTextCardItemHolder = viewHolder as ItemTextCardItemHolder
        var txtCard: Map<String, Object> = data.data as Map<String, Object>
        var actionUrl = txtCard["actionUrl"]
        var type = txtCard["type"]
        if ("header5".equals(type) || type.toString().contains("header")) {
            holder.ll_header5!!.visibility = View.VISIBLE
            holder.rl_footer2!!.visibility = View.GONE
            holder.tv_title!!.text = txtCard!!["text"].toString()
            if (TextUtils.isEmpty(actionUrl.toString())) {
                holder.iv_more_header!!.visibility = View.GONE
            } else {
                holder.iv_more_header!!.visibility = View.VISIBLE
            }
        } else if ("footer2".equals(type) || type.toString().contains("footer")) {
            holder.ll_header5!!.visibility = View.GONE
            holder.rl_footer2!!.visibility = View.VISIBLE
            holder.tv_footer!!.text = txtCard!!["text"].toString()
            if (TextUtils.isEmpty(actionUrl.toString())) {
                holder.iv_more!!.visibility = View.GONE
            } else {
                holder.iv_more!!.visibility = View.VISIBLE
            }
        }

    }

    private fun onItemBriefCardBind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas[position]
        var holder: ItemBriefCardItemHolder = viewHolder as ItemBriefCardItemHolder
        var briefCard: Map<String, Object> = data.data as Map<String, Object>
        holder.tv_title!!.text = briefCard["title"].toString()
        holder.tv_content!!.text = briefCard["description"].toString()

        ImageLoad().loadCircle(mContext, briefCard["icon"].toString(), holder.image)
    }

    private fun onItemDynamicInfoCardBind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas[position]
        var holder: ItemDynamicInfoCardItemHolder = viewHolder as ItemDynamicInfoCardItemHolder
        var dynamicInfoCard: Map<String, Object> = data.data as Map<String, Object>
        holder.tv_print!!.text = dynamicInfoCard["text"].toString()
        var user: Map<String, Object> = dynamicInfoCard["user"] as Map<String, Object>

        ImageLoad().loadCircle(mContext, user["avatar"].toString(), holder.civ_icon)
        holder.tv_nickname!!.text = user["nickname"].toString()
        var reply: Map<String, Object> = dynamicInfoCard["reply"] as Map<String, Object>
        holder.tv_content!!.text = reply["message"].toString()
        holder.tv_like!!.text = "赞" + reply["likeCount"].toString().toFloat().toInt()


        var simpleVideo: Map<String, Object> = dynamicInfoCard["simpleVideo"] as Map<String, Object>
        holder.tv_title!!.text = simpleVideo["title"].toString()
        holder.tv_des!!.text = "#" + simpleVideo["category"].toString()
        var cover: Map<String, Object> = simpleVideo["cover"] as Map<String, Object>

        var width = getScreenWidth(mContext) * 0.4
        var height = width * 0.6
        ImageLoad().load(mContext, cover["feed"].toString(), holder.iv_conver, width.toDouble().toInt(), height.toDouble().toInt(), 5)

        var obj: Double = simpleVideo["releaseTime"].toString().toDouble()
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
        var data: Result.ItemList = datas[position]
        var holder: ItemFollowCardItemHolder = viewHolder as ItemFollowCardItemHolder
        var followCard: Map<String, Object> = data.data as Map<String, Object>
        var header: Map<String, Object> = followCard["header"] as Map<String, Object>
        ImageLoad().loadCircle(mContext, header["icon"].toString(), holder.iv_icon)

        var list = TextUtils.split(header["description"].toString(), "/")
        holder.tv_content!!.text = header["title"].toString() + " / " + list[0]

        var content: Map<String, Object> = followCard["content"] as Map<String, Object>
        var datas: Map<String, Object> = content["data"] as Map<String, Object>
        var cover: Map<String, Object> = datas["cover"] as Map<String, Object>
        var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
        var height = width * 0.6
        ImageLoad().load(mContext, cover["feed"].toString(), holder.iv_cover, width, height.toDouble().toInt(), 5)

        holder.tv_time!!.text = TimeUtils.secToTime(datas["duration"].toString().toFloat().toInt())

        holder.tv_title!!.text = datas["title"].toString()
    }

    private fun onItemVideoSmallCardBinder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas[position]
        var holder: ItemVideoSmallCardHolder = viewHolder as ItemVideoSmallCardHolder
        var videoSmallCard: Map<String, Object> = data.data as Map<String, Object>
        var cover: Map<String, Object> = videoSmallCard["cover"] as Map<String, Object>
        var width = getScreenWidth(mContext) * 0.5
        var height = width * 0.6
        ImageLoad().load(mContext, cover["feed"].toString(), holder.iv_cover, width.toDouble().toInt(), height.toDouble().toInt(), 5)

        holder.tv_time!!.text = TimeUtils.secToTime(videoSmallCard["duration"].toString().toFloat().toInt())
        holder.tv_title!!.text = videoSmallCard["title"].toString()
        if (videoSmallCard["author"] != null) {
            var author: Map<String, Object> = videoSmallCard["author"] as Map<String, Object>
            holder.tv_content!!.text = "#" + videoSmallCard["category"] + " / " + author["name"].toString().replace(videoSmallCard["category"].toString(), "")
        }
    }

    private fun onItemSquareCardCollectionBinder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas[position]
        var holder: ItemSquareCardCollectionHolder = viewHolder as ItemSquareCardCollectionHolder
        var squareCardCollection: Map<String, Object> = data.data as Map<String, Object>
        var header: Map<String, Object> = squareCardCollection["header"] as Map<String, Object>
        holder.tv_title!!.text = header["title"].toString()

        var itemList: List<Map<String, Object>> = squareCardCollection["itemList"] as List<Map<String, Object>>
        var urlList: MutableList<String>? = ArrayList<String>()
        for (map in itemList) {
            var type = map["type"]
            var data: Map<String, Object> = map["data"] as Map<String, Object>
            if (ITEM_TYPE.ITEM_FOLLOWCARD.type.hashCode() == type.toString().hashCode()) {
                var content: Map<String, Object> = data["content"] as Map<String, Object>
                var datas: Map<String, Object> = content["data"] as Map<String, Object>
                var cover: Map<String, Object> = datas["cover"] as Map<String, Object>
                urlList!!.add(cover["feed"].toString())
            } else {
                urlList!!.add(data["image"].toString())
            }


        }

        var linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerview!!.layoutManager = linearLayout
        var snapHelper: SnapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(holder.recyclerview)


        var adapter = GalleryAdapter(mContext, urlList!!.toList())
        holder.recyclerview!!.adapter = adapter

    }

    private fun onItemVideoCollectionWithBriefBinder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas[position]
        var holder: ItemVideoCollectionWithBriefHolder = viewHolder as ItemVideoCollectionWithBriefHolder
        var videoCollectionWithBrief: Map<String, Object> = data.data as Map<String, Object>

        var header: Map<String, Object> = videoCollectionWithBrief["header"] as Map<String, Object>
        holder.tv_nickname!!.text = header["title"].toString()
        ImageLoad().loadCircle(mContext, header["icon"].toString(), holder.iv_icon)
        holder.tv_des!!.text = header["description"].toString()

        var itemList: List<Map<String, Object>> = videoCollectionWithBrief["itemList"] as List<Map<String, Object>>
        var urlList: MutableList<VideoCollectionAdapter.VideoCollection>? = ArrayList<VideoCollectionAdapter.VideoCollection>()
        for (map in itemList) {
            var data: Map<String, Object> = map["data"] as Map<String, Object>
            var cover: Map<String, Object> = data["cover"] as Map<String, Object>
            var video: VideoCollectionAdapter.VideoCollection = VideoCollectionAdapter.VideoCollection()
            video.icon = cover["feed"].toString()
            video.title = data["title"].toString()
            video.category = data["category"].toString()
            urlList!!.add(video)
        }

        var linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerview!!.layoutManager = linearLayout
        var snapHelper: SnapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(holder.recyclerview)


        var adapter = VideoCollectionAdapter(mContext, urlList!!.toList())
        holder.recyclerview!!.adapter = adapter

    }

    private fun onItemBannerBind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas[position]
        var holder: ItemBannerHolder = viewHolder as ItemBannerHolder
        var banner: Map<String, Object> = data.data as Map<String, Object>
        var image: String = banner["image"].toString()
        var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
        var height = width * 0.6
        ImageLoad().load(mContext, image, holder.iv_banner, width.toDouble().toInt(), height.toDouble().toInt(), 5)
    }

    private fun onItemVideoBind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas[position]
        var holder: ItemVideoItemHolder = viewHolder as ItemVideoItemHolder
        var video: Map<String, Object> = data.data as Map<String, Object>
        var author: Map<String, Object> = video["author"] as Map<String, Object>
        ImageLoad().loadCircle(mContext, author["icon"].toString(), holder.iv_icon)

        holder.tv_content!!.text = author["name"].toString() + " / #" + video["category"]

        var cover: Map<String, Object> = video["cover"] as Map<String, Object>
        var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
        var height = width * 0.6
        ImageLoad().load(mContext, cover["feed"].toString(), holder.iv_cover, width, height.toDouble().toInt(), 5)

        holder.tv_time!!.text = TimeUtils.secToTime(video["duration"].toString().toFloat().toInt())

        holder.tv_title!!.text = video["title"].toString()
    }

    private fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }


    //将数据与界面进行绑定的操作
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is ItemTextCardItemHolder -> onItemTextCardBind(viewHolder, position)
            is ItemBriefCardItemHolder -> onItemBriefCardBind(viewHolder, position)
            is ItemDynamicInfoCardItemHolder -> onItemDynamicInfoCardBind(viewHolder, position)
            is ItemHorizontalScrollCardHolder -> onItemHorizontalScrollCardBind(viewHolder, position)
            is ItemFollowCardItemHolder -> onItemFollowCardBind(viewHolder, position)
            is ItemVideoSmallCardHolder -> onItemVideoSmallCardBinder(viewHolder, position)
            is ItemSquareCardCollectionHolder -> onItemSquareCardCollectionBinder(viewHolder, position)
            is ItemVideoCollectionWithBriefHolder -> onItemVideoCollectionWithBriefBinder(viewHolder, position)
            is ItemBannerHolder -> onItemBannerBind(viewHolder, position)
            is ItemVideoItemHolder -> onItemVideoBind(viewHolder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        var type: String = datas!![position].type!!
        return type.hashCode()
    }

    //获取数据的数量
    override fun getItemCount(): Int = datas.size

    inner class ItemTextCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_title: TextView? = itemView.tv_text_card_title
        var ll_header5: LinearLayout? = itemView.ll_header5
        var rl_footer2: RelativeLayout? = itemView.rl_footer2
        var tv_footer: TextView? = itemView.tv_footer
        var iv_more_header: ImageView? = itemView.iv_more_header
        var iv_more: ImageView? = itemView.iv_more
    }

    inner class ItemBriefCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView? = itemView.image
        var tv_title: TextView? = itemView.tv_brief_card_title
        var tv_content: TextView? = itemView.tv_brief_card_content
    }

    inner class ItemDynamicInfoCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var civ_icon: ImageView? = itemView.civ_icon
        var tv_nickname: TextView? = itemView.tv_dynamic_nickname
        var tv_print: TextView? = itemView.tv_print
        var tv_content: TextView? = itemView.tv_dynamic_content
        var iv_conver: ImageView? = itemView.iv_conver
        var tv_title: TextView? = itemView.tv_dynamic_title
        var tv_des: TextView? = itemView.tv_dynamic_des
        var tv_like: TextView? = itemView.tv_like
        var tv_time: TextView? = itemView.tv_dynamic_time
    }

    inner class ItemHorizontalScrollCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recyclerview: RecyclerView? = itemView.recyclerview_horizontal_scrollcard
    }

    inner class ItemFollowCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_cover: ImageView? = itemView.iv_followcard_cover
        var tv_time: TextView? = itemView.tv_followcard_time
        var iv_icon: ImageView? = itemView.iv_followcard_icon
        var tv_title: TextView? = itemView.tv_followcard_title
        var tv_content: TextView? = itemView.tv_followcard_content
    }

    inner class ItemVideoSmallCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_cover: ImageView? = itemView.iv_cover
        var tv_time: TextView? = itemView.tv_time
        var tv_title: TextView? = itemView.tv_smallcard_title
        var tv_content: TextView? = itemView.tv_smallcard_content
    }

    inner class ItemSquareCardCollectionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recyclerview: RecyclerView? = itemView.recyclerview_squarecard_collection
        var tv_title: TextView? = itemView.tv_squarecard_collection_title
    }

    inner class ItemVideoCollectionWithBriefHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_icon: ImageView? = itemView.iv_videocollection_icon
        var tv_nickname: TextView? = itemView.tv_videocollection_nickname
        var tv_des: TextView? = itemView.tv_videocollection_des
        var recyclerview: RecyclerView? = itemView.recyclerview_videocollection
    }

    inner class ItemBannerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_banner: ImageView? = itemView.iv_banner
    }

    inner class ItemVideoItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_cover: ImageView? = itemView.iv_followcard_cover
        var tv_time: TextView? = itemView.tv_followcard_time
        var iv_icon: ImageView? = itemView.iv_followcard_icon
        var tv_title: TextView? = itemView.tv_followcard_title
        var tv_content: TextView? = itemView.tv_followcard_content
    }

    inner class ItemEmptyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        init {

        }
    }

}
