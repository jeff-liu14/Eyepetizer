package com.moment.eyepetizer.home.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.moment.eyepetizer.net.entity.Result
import com.moment.eyepetizer.utils.*
import java.lang.ref.WeakReference

/**
 * Created by moment on 2018/2/9.
 */

fun bindViewHolder(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    when (viewHolder) {
        is ItemTextCardItemHolder -> onItemTextCardBind(mContext, datas, viewHolder, position)
        is ItemBriefCardItemHolder -> onItemBriefCardBind(mContext, datas, viewHolder, position)
        is ItemDynamicInfoCardItemHolder -> onItemDynamicInfoCardBind(mContext, datas, viewHolder, position)
        is ItemHorizontalScrollCardHolder -> onItemHorizontalScrollCardBind(mContext, datas, viewHolder, position)
        is ItemFollowCardItemHolder -> onItemFollowCardBind(mContext, datas, viewHolder, position)
        is ItemVideoSmallCardHolder -> onItemVideoSmallCardBinder(mContext, datas, viewHolder, position)
        is ItemSquareCardCollectionHolder -> onItemSquareCardCollectionBinder(mContext, datas, viewHolder, position)
        is ItemVideoCollectionWithBriefHolder -> onItemVideoCollectionWithBriefBinder(mContext, datas, viewHolder, position)
        is ItemBannerHolder -> onItemBannerBind(mContext, datas, viewHolder, position)
        is ItemVideoItemHolder -> onItemVideoBind(mContext, datas, viewHolder, position)
    }
}

fun onItemTextCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemTextCardItemHolder = viewHolder as ItemTextCardItemHolder
    var txtCard: Map<String, Object> = data.data as Map<String, Object>
    var actionUrl = txtCard["actionUrl"]
    var type = txtCard["type"]
    if ("header5".equals(type) || type.toString().contains("header")) {
        holder.ll_header5!!.visibility = View.VISIBLE
        holder.rl_footer2!!.visibility = View.GONE
        holder.tv_title!!.text = txtCard!!["text"].toString()
        if (actionUrl == null || TextUtils.isEmpty(actionUrl.toString())) {
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

fun onItemBriefCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemBriefCardItemHolder = viewHolder as ItemBriefCardItemHolder
    var briefCard: Map<String, Object> = data.data as Map<String, Object>
    holder.tv_title!!.text = briefCard["title"].toString()
    holder.tv_content!!.text = briefCard["description"].toString()
    holder.rl_brief_root.setOnClickListener {
        parseUri(mContext, briefCard["actionUrl"].toString())
    }

    ImageLoad().loadCircle(WeakReference(mContext), briefCard["icon"].toString(), holder.image)
}

fun onItemDynamicInfoCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemDynamicInfoCardItemHolder = viewHolder as ItemDynamicInfoCardItemHolder
    var dynamicInfoCard: Map<String, Object> = data.data as Map<String, Object>
    holder.tv_print!!.text = dynamicInfoCard["text"].toString()
    var user: Map<String, Object> = dynamicInfoCard["user"] as Map<String, Object>

    ImageLoad().loadCircle(WeakReference(mContext), user["avatar"].toString(), holder.civ_icon)
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
    ImageLoad().load(WeakReference(mContext), cover["feed"].toString(), holder.iv_conver, width.toDouble().toInt(), height.toDouble().toInt(), 5)

    var obj: Double = simpleVideo["releaseTime"].toString().toDouble()
    holder.tv_time!!.text = TimeUtils.getDiffTime(obj.toLong())
}

fun onItemHorizontalScrollCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
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
    ItemClickSupport.addTo(holder.recyclerview!!)!!.setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
        override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
            var map: Map<String, Object> = itemList[position] as Map<String, Object>
            var data: Map<String, Object> = map["data"] as Map<String, Object>
            var url = data["actionUrl"]
            parseUri(mContext, url.toString())
        }

    })
}

fun onItemFollowCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemFollowCardItemHolder = viewHolder as ItemFollowCardItemHolder
    var followCard: Map<String, Object> = data.data as Map<String, Object>
    var header: Map<String, Object> = followCard["header"] as Map<String, Object>
    ImageLoad().loadCircle(WeakReference(mContext), header["icon"].toString(), holder.iv_icon)

    var list = TextUtils.split(header["description"].toString(), "/")
    holder.tv_content!!.text = header["title"].toString() + " / " + list[0]

    var content: Map<String, Object> = followCard["content"] as Map<String, Object>
    var datas: Map<String, Object> = content["data"] as Map<String, Object>
    var cover: Map<String, Object> = datas["cover"] as Map<String, Object>
    var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
    var height = width * 0.6
    ImageLoad().load(WeakReference(mContext), cover["feed"].toString(), holder.iv_cover, width, height.toDouble().toInt(), 5)

    holder.tv_time!!.text = TimeUtils.secToTime(datas["duration"].toString().toFloat().toInt())

    holder.tv_title!!.text = datas["title"].toString()
}

fun onItemVideoSmallCardBinder(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemVideoSmallCardHolder = viewHolder as ItemVideoSmallCardHolder
    var videoSmallCard: Map<String, Object> = data.data as Map<String, Object>
    var cover: Map<String, Object> = videoSmallCard["cover"] as Map<String, Object>
    var width = getScreenWidth(mContext) * 0.5
    var height = width * 0.6
    ImageLoad().load(WeakReference(mContext), cover["feed"].toString(), holder.iv_cover, width.toDouble().toInt(), height.toDouble().toInt(), 5)

    holder.tv_time!!.text = TimeUtils.secToTime(videoSmallCard["duration"].toString().toFloat().toInt())
    holder.tv_title!!.text = videoSmallCard["title"].toString()
    if (videoSmallCard["author"] != null) {
        var author: Map<String, Object> = videoSmallCard["author"] as Map<String, Object>
        holder.tv_content!!.text = "#" + videoSmallCard["category"] + " / " + author["name"].toString().replace(videoSmallCard["category"].toString(), "")
    }
}

fun onItemSquareCardCollectionBinder(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
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
        if (MyMultiTypeAdapter.ITEM_TYPE.ITEM_FOLLOWCARD.type.hashCode() == type.toString().hashCode()) {
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

fun onItemVideoCollectionWithBriefBinder(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemVideoCollectionWithBriefHolder = viewHolder as ItemVideoCollectionWithBriefHolder
    var videoCollectionWithBrief: Map<String, Object> = data.data as Map<String, Object>

    var header: Map<String, Object> = videoCollectionWithBrief["header"] as Map<String, Object>
    holder.tv_nickname!!.text = header["title"].toString()
    ImageLoad().loadCircle(WeakReference(mContext), header["icon"].toString(), holder.iv_icon)
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
        video.duration = data["duration"].toString().toFloat().toInt().toLong()
        urlList!!.add(video)
    }

    var linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    holder.recyclerview!!.layoutManager = linearLayout
    var snapHelper: SnapHelper = GravitySnapHelper(Gravity.START)
    snapHelper.attachToRecyclerView(holder.recyclerview)


    var adapter = VideoCollectionAdapter(mContext, urlList!!.toList())
    holder.recyclerview!!.adapter = adapter

}

fun onItemBannerBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemBannerHolder = viewHolder as ItemBannerHolder
    var banner: Map<String, Object> = data.data as Map<String, Object>
    var image: String = banner["image"].toString()
    var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
    var height = width * 0.6
    ImageLoad().load(WeakReference(mContext), image, holder.iv_banner, width.toDouble().toInt(), height.toDouble().toInt(), 5)
}

fun onItemVideoBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemVideoItemHolder = viewHolder as ItemVideoItemHolder
    var video: Map<String, Object> = data.data as Map<String, Object>
    if (video["author"] != null) {
        var author: Map<String, Object> = video["author"] as Map<String, Object>
        ImageLoad().loadCircle(WeakReference(mContext), author["icon"].toString(), holder.iv_icon)
        holder.tv_content!!.text = author["name"].toString() + " / #" + video["category"]
    } else {
        var tags: List<Map<String, Object>> = video["tags"] as List<Map<String, Object>>
        ImageLoad().load(WeakReference(mContext), tags[0]["headerImage"].toString(), holder.iv_icon, 5)
        holder.tv_content!!.text = "#" + tags[0]["name"].toString() + "#"
    }

    var cover: Map<String, Object> = video["cover"] as Map<String, Object>
    var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
    var height = width * 0.6
    ImageLoad().load(WeakReference(mContext), cover["feed"].toString(), holder.iv_cover, width, height.toDouble().toInt(), 5)

    holder.tv_time!!.text = TimeUtils.secToTime(video["duration"].toString().toFloat().toInt())

    holder.tv_title!!.text = video["title"].toString()

}