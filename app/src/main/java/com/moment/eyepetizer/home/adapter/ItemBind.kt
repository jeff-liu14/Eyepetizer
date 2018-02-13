package com.moment.eyepetizer.home.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.text.TextUtils
import android.util.TypedValue
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
        is ItemBanner2Holder -> onItemBanner2Bind(mContext, datas, viewHolder, position)
        is ItemVideoItemHolder -> onItemVideoBind(mContext, datas, viewHolder, position)
        is ItemVideoCollectionOfHolder -> onItemVideoCollectionOfBind(mContext, datas, viewHolder, position)
        is ItemTextHeaderItemHolder -> onItemTextHeaderBind(mContext, datas, viewHolder, position)
        is ItemTextFooterItemHolder -> onItemTextFooterBind(mContext, datas, viewHolder, position)
    }
}

fun onItemTextCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemTextCardItemHolder = viewHolder as ItemTextCardItemHolder
    var txtCard: Map<String, Object> = data.data as Map<String, Object>
    var actionUrl = txtCard["actionUrl"]
    var type = txtCard["type"]
    if ("header5" == type.toString() || type.toString().contains("header")) {
        holder.ll_header5!!.visibility = View.VISIBLE
        holder.rl_footer2!!.visibility = View.GONE
        holder.tv_title!!.text = txtCard!!["text"].toString()
        if (actionUrl == null || TextUtils.isEmpty(actionUrl.toString())) {
            holder.iv_more_header!!.visibility = View.GONE
        } else {
            holder.iv_more_header!!.visibility = View.VISIBLE
            holder.ll_header5!!.setOnClickListener {
                parseUri(mContext, actionUrl.toString())
            }
        }
    } else if ("footer2" == type.toString() || type.toString().contains("footer")) {
        holder.ll_header5!!.visibility = View.GONE
        holder.rl_footer2!!.visibility = View.VISIBLE
        holder.tv_footer!!.text = txtCard!!["text"].toString()
        if (actionUrl == null || TextUtils.isEmpty(actionUrl.toString())) {
            holder.iv_more!!.visibility = View.GONE
        } else {
            holder.iv_more!!.visibility = View.VISIBLE
            holder.rl_footer2!!.setOnClickListener {
                parseUri(mContext, actionUrl.toString())
            }
        }
    }

}

fun onItemTextHeaderBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemTextHeaderItemHolder = viewHolder as ItemTextHeaderItemHolder
    var txtCard: Map<String, Object> = data.data as Map<String, Object>
    holder.tv_title!!.text = txtCard["text"].toString()
    var actionUrl = txtCard["actionUrl"]
    if (actionUrl != null) {
        if (actionUrl.toString().isEmpty()) {
            holder.iv_text_header_more!!.visibility = View.GONE
        } else {
            holder.iv_text_header_more!!.visibility = View.VISIBLE
        }
    } else {
        holder.iv_text_header_more!!.visibility = View.GONE
    }

    holder.tv_title!!.setOnClickListener {
        if (actionUrl != null) {
            parseUri(mContext, actionUrl.toString())
        }
    }

}

fun onItemTextFooterBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemTextFooterItemHolder = viewHolder as ItemTextFooterItemHolder
    var txtCard: Map<String, Object> = data.data as Map<String, Object>
    holder.tv_text_footer!!.text = txtCard["text"].toString()
    var actionUrl = txtCard["actionUrl"]
    if (actionUrl != null) {
        if (actionUrl.toString().isEmpty()) {
            holder.iv_footer_more!!.visibility = View.GONE
        } else {
            holder.iv_footer_more!!.visibility = View.VISIBLE
        }
    } else {
        holder.iv_footer_more!!.visibility = View.GONE
    }

    holder.tv_text_footer!!.setOnClickListener {
        if (actionUrl != null) {
            parseUri(mContext, actionUrl.toString())
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
    var iconType = briefCard["iconType"].toString()
    when (iconType) {
        "square" -> ImageLoad().load(WeakReference(mContext), briefCard["icon"].toString(), holder.image, 5)
        "round" -> ImageLoad().loadCircle(WeakReference(mContext), briefCard["icon"].toString(), holder.image)
        else -> ImageLoad().load(WeakReference(mContext), briefCard["icon"].toString(), holder.image)
    }

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

    holder.iv_conver!!.setOnClickListener {
        var title = simpleVideo["simpleVideo"].toString()

    }
    var obj: Double = simpleVideo["releaseTime"].toString().toDouble()
    holder.tv_time!!.text = TimeUtils.getDiffTime(obj.toLong())
}

fun onItemHorizontalScrollCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas.get(position)
    var holder: ItemHorizontalScrollCardHolder = viewHolder as ItemHorizontalScrollCardHolder
    var horizontalScrollCard: Map<String, Object> = data.data as Map<String, Object>
    var itemList: List<Map<String, Object>> = horizontalScrollCard.get("itemList") as List<Map<String, Object>>
    var urlList: MutableList<String>? = ArrayList<String>()
    itemList
            .map { it["data"] as Map<String, Object> }
            .forEach { urlList!!.add(it["image"].toString()) }

    var linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    holder.recyclerview!!.layoutManager = linearLayout
    var snapHelper: SnapHelper = GravitySnapHelper(Gravity.START)
    snapHelper.attachToRecyclerView(holder.recyclerview)


    var adapter = GalleryAdapter(mContext, urlList!!.toList())
    holder.recyclerview!!.adapter = adapter
    ItemClickSupport.addTo(holder.recyclerview!!)!!.setOnItemClickListener { _, position, _ ->
        var map: Map<String, Object> = itemList[position] as Map<String, Object>
        var data: Map<String, Object> = map["data"] as Map<String, Object>
        var url = data["actionUrl"]
        parseUri(mContext, url.toString())
    }
}

fun onItemFollowCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemFollowCardItemHolder = viewHolder as ItemFollowCardItemHolder
    var followCard: Map<String, Object> = data.data as Map<String, Object>
    var header: Map<String, Object> = followCard["header"] as Map<String, Object>
    var iconType = header["iconType"].toString()

    when (iconType) {
        "square" -> ImageLoad().load(WeakReference(mContext), header["icon"].toString(), holder.iv_icon, 5)
        "round" -> ImageLoad().loadCircle(WeakReference(mContext), header["icon"].toString(), holder.iv_icon)
        else -> ImageLoad().load(WeakReference(mContext), header["icon"].toString(), holder.iv_icon)
    }

    var list = TextUtils.split(header["description"].toString(), "/")
    holder.tv_content!!.text = header["title"].toString() + " / " + list[0]

    var content: Map<String, Object> = followCard["content"] as Map<String, Object>
    var datas: Map<String, Object> = content["data"] as Map<String, Object>
    var cover: Map<String, Object> = datas["cover"] as Map<String, Object>
    var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
    var height = width * 0.6
    ImageLoad().load(WeakReference(mContext), cover["feed"].toString(), holder.iv_cover, width, height.toDouble().toInt(), 5)

    holder.iv_cover!!.setOnClickListener {
        var title = datas["title"].toString()
        var webUrl = datas["webUrl"] as Map<String, Object>
        var url = webUrl["raw"].toString()
        parseUri(mContext, parseWebView(title, url))
    }
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

    holder.iv_cover!!.setOnClickListener {
        var title = videoSmallCard["title"].toString()
        var webUrl = videoSmallCard["webUrl"] as Map<String, Object>
        var url = webUrl["raw"].toString()
        parseUri(mContext, parseWebView(title, url))
    }
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
    if (header["font"] != null && "bigBold" == header["font"].toString()) {
        holder.tv_title!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f)
    }

    if (header["subTitle"] != null) {
        holder.tv_squarecard_collection_sub_title!!.text = header["subTitle"].toString()
        if (header["subTitleFont"] != null && "lobster" == header["subTitleFont"].toString()) {
            holder.tv_squarecard_collection_sub_title!!.typeface = Typeface.createFromAsset(mContext.assets, "fonts/Lobster-1.4.otf")
        }
        holder.tv_squarecard_collection_sub_title!!.visibility = View.VISIBLE
    } else {
        holder.tv_squarecard_collection_sub_title!!.visibility = View.GONE
    }


    var actionUrl = header["actionUrl"].toString()

    if (!actionUrl.isEmpty()) {
        holder.iv_squarecard_more!!.visibility = View.VISIBLE
    } else {
        holder.iv_squarecard_more!!.visibility = View.GONE
    }

    holder.tv_title!!.setOnClickListener {
        if (!actionUrl.isEmpty()) {
            parseUri(mContext, actionUrl)
        }
    }


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
    ItemClickSupport.addTo(holder.recyclerview).setOnItemClickListener { recyclerView, position, v ->
        var map: Map<String, Object> = itemList[position]
        var data: Map<String, Object> = map["data"] as Map<String, Object>
        if (data[actionUrl] != null) {
            var actionUrl = data["actionUrl"].toString()
            if (!actionUrl.isEmpty()) {
                parseUri(mContext, actionUrl)
            }
        }
        if (data["content"] != null) {
            var content = data["content"] as Map<String, Object>
            if (content["data"] != null) {
                var videodata = content["data"] as Map<String, Object>
                var title = videodata["title"].toString()
                if (videodata["webUrl"] != null) {
                    var webUrl = videodata["webUrl"] as Map<String, Object>
                    var raw = webUrl["raw"].toString()
                    parseUri(mContext, parseWebView(title, raw))
                }
            }
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

    var iconType = header["iconType"].toString()
    when (iconType) {
        "square" -> ImageLoad().load(WeakReference(mContext), header["icon"].toString(), holder.iv_icon, 5)
        "round" -> ImageLoad().loadCircle(WeakReference(mContext), header["icon"].toString(), holder.iv_icon)
        else -> ImageLoad().load(WeakReference(mContext), header["icon"].toString(), holder.iv_icon)
    }
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

    ItemClickSupport.addTo(holder.recyclerview).setOnItemClickListener { recyclerView, position, v ->
        var data: Map<String, Object> = itemList[position]["data"] as Map<String, Object>
        var title = data["title"].toString()
        var webUrl = data["webUrl"] as Map<String, Object>
        var url = webUrl["raw"].toString()
        parseUri(mContext, parseWebView(title, url))

    }
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
    holder.iv_banner!!.setOnClickListener {
        var actionUrl = banner["actionUrl"].toString()
        parseUri(mContext, actionUrl)
    }
}

fun onItemBanner2Bind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemBanner2Holder = viewHolder as ItemBanner2Holder
    var banner: Map<String, Object> = data.data as Map<String, Object>
    var image: String = banner["image"].toString()
    var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
    var height = width * 0.6
    ImageLoad().load(WeakReference(mContext), image, holder.iv_banner, width.toDouble().toInt(), height.toDouble().toInt(), 5)

    var label = banner["label"]
    if (label != null) {
        var label: Map<String, Object> = banner["label"] as Map<String, Object>
        var text = label["text"]
        if (!text.toString().isEmpty()) {
            holder.tv_banner2!!.text = label["text"].toString()
            holder.tv_banner2!!.visibility = View.VISIBLE
        } else {
            holder.tv_banner2!!.visibility = View.GONE
        }
    } else {
        holder.tv_banner2!!.visibility = View.GONE
    }

    var actionUrl = banner["actionUrl"].toString()
    holder.iv_banner!!.setOnClickListener {
        if (!actionUrl.isEmpty()) {
            parseUri(mContext, actionUrl)
        }
    }


}

fun onItemVideoBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemVideoItemHolder = viewHolder as ItemVideoItemHolder
    var video: Map<String, Object> = data.data as Map<String, Object>
    if (video["author"] != null) {
        var author: Map<String, Object> = video["author"] as Map<String, Object>
        ImageLoad().loadCircle(WeakReference(mContext), author["icon"].toString(), holder.iv_icon)
        holder.tv_content!!.text = author["name"].toString() + " / #" + video["category"]
    } else if (video["tags"] != null) {
        var tags: List<Map<String, Object>> = video["tags"] as List<Map<String, Object>>
        ImageLoad().load(WeakReference(mContext), tags[0]["headerImage"].toString(), holder.iv_icon, 5)
        holder.tv_content!!.text = "#" + tags[0]["name"].toString() + "#"
    }

    var cover: Map<String, Object> = video["cover"] as Map<String, Object>
    var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
    var height = width * 0.6
    ImageLoad().load(WeakReference(mContext), cover["feed"].toString(), holder.iv_cover, width, height.toDouble().toInt(), 5)

    holder.iv_cover!!.setOnClickListener {
        var title = video["title"].toString()
        var webUrl = video["webUrl"] as Map<String, Object>
        var url = webUrl["raw"].toString()
        parseUri(mContext, parseWebView(title, url))
    }
    holder.tv_time!!.text = TimeUtils.secToTime(video["duration"].toString().toFloat().toInt())

    holder.tv_title!!.text = video["title"].toString()

}

fun onItemVideoCollectionOfBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    var data: Result.ItemList = datas[position]
    var holder: ItemVideoCollectionOfHolder = viewHolder as ItemVideoCollectionOfHolder
    var videoCollectionOfHorizontalScrollCard: Map<String, Object> = data.data as Map<String, Object>

    var header: Map<String, Object> = videoCollectionOfHorizontalScrollCard["header"] as Map<String, Object>
    holder.tv_videocollection_of_title!!.text = header["title"].toString()
    var actionUrl = header["actionUrl"]

    holder.tv_videocollection_of_title!!.setOnClickListener {
        if (actionUrl != null) {
            parseUri(mContext, actionUrl.toString())
        }
    }
    if (actionUrl == null || TextUtils.isEmpty(actionUrl.toString())) {
        holder.iv_videocollection_of_header!!.visibility = View.GONE
    } else {
        holder.iv_videocollection_of_header!!.visibility = View.VISIBLE
    }

    var itemList: List<Map<String, Object>> = videoCollectionOfHorizontalScrollCard["itemList"] as List<Map<String, Object>>
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
    holder.recyclerview_videocollection_of!!.layoutManager = linearLayout
    var snapHelper: SnapHelper = GravitySnapHelper(Gravity.START)
    snapHelper.attachToRecyclerView(holder.recyclerview_videocollection_of)
    ItemClickSupport.addTo(holder.recyclerview_videocollection_of).setOnItemClickListener { recyclerView, position, v ->
        var data: Map<String, Object> = itemList[position]["data"] as Map<String, Object>
        var title = data["title"].toString()
        var webUrl = data["webUrl"] as Map<String, Object>
        var url = webUrl["raw"].toString()
        parseUri(mContext, parseWebView(title, url))
    }

    var adapter = VideoCollectionAdapter(mContext, urlList!!.toList())
    holder.recyclerview_videocollection_of!!.adapter = adapter
}