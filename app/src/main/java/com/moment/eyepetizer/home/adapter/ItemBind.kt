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
import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper
import com.moment.eyepetizer.net.entity.Result
import com.moment.eyepetizer.utils.*

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
    val data: Result.ItemList = datas[position]
    val holder: ItemTextCardItemHolder = viewHolder as ItemTextCardItemHolder
    val txtCard = data.data as Map<*, *>
    val actionUrl = txtCard["actionUrl"]
    val type = txtCard["type"]
    if ("header5" == type.toString() || type.toString().contains("header")) {
        holder.ll_header5!!.visibility = View.VISIBLE
        holder.rl_footer2!!.visibility = View.GONE
        holder.tv_title!!.text = txtCard["text"].toString()
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
        holder.tv_footer!!.text = txtCard["text"].toString()
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
    val data: Result.ItemList = datas[position]
    val holder: ItemTextHeaderItemHolder = viewHolder as ItemTextHeaderItemHolder
    val txtCard = data.data as Map<*, *>
    holder.tv_title!!.text = txtCard["text"].toString()
    val actionUrl = txtCard["actionUrl"]
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
    val data: Result.ItemList = datas[position]
    val holder: ItemTextFooterItemHolder = viewHolder as ItemTextFooterItemHolder
    val txtCard = data.data as Map<*, *>
    holder.tv_text_footer!!.text = txtCard["text"].toString()
    val actionUrl = txtCard["actionUrl"]
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
    val data: Result.ItemList = datas[position]
    val holder: ItemBriefCardItemHolder = viewHolder as ItemBriefCardItemHolder
    val briefCard = data.data as Map<*, *>
    holder.tv_title!!.text = briefCard["title"].toString()
    holder.tv_content!!.text = briefCard["description"].toString()
    holder.rl_brief_root.setOnClickListener {
        parseUri(mContext, briefCard["actionUrl"].toString())
    }
    val iconType = briefCard["iconType"].toString()
    when (iconType) {
        "square" -> ImageLoad().loadRound(briefCard["icon"].toString(), holder.image, 5)
        "round" -> ImageLoad().loadCircle(briefCard["icon"].toString(), holder.image)
        else -> ImageLoad().load(briefCard["icon"].toString(), holder.image)
    }

}

fun onItemDynamicInfoCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemDynamicInfoCardItemHolder = viewHolder as ItemDynamicInfoCardItemHolder
    val dynamicInfoCard = data.data as Map<*, *>
    holder.tv_print!!.text = dynamicInfoCard["text"].toString()
    val user = dynamicInfoCard["user"] as Map<*, *>

    ImageLoad().loadCircle(user["avatar"].toString(), holder.civ_icon)
    holder.tv_nickname!!.text = user["nickname"].toString()
    val reply = dynamicInfoCard["reply"] as Map<*, *>
    holder.tv_content!!.text = reply["message"].toString()
    holder.tv_like!!.text = "赞" + reply["likeCount"].toString().toFloat().toInt()


    val simpleVideo = dynamicInfoCard["simpleVideo"] as Map<*, *>
    holder.tv_title!!.text = simpleVideo["title"].toString()
    holder.tv_des!!.text = "#" + simpleVideo["category"].toString()
    val cover = simpleVideo["cover"] as Map<*, *>
    holder.tv_dynamic_time_video!!.text = TimeUtils.secToTime(simpleVideo["duration"].toString().toFloat().toInt())

    val width = getScreenWidth(mContext) * 0.4
    val height = width * 0.6
    ImageLoad().load(cover["feed"].toString(), holder.iv_conver, width.toInt(), height.toInt(), 5)

    holder.iv_conver!!.setOnClickListener {
        var title = simpleVideo["title"].toString()
        var id = simpleVideo["id"]
    }
    val obj: Double = simpleVideo["releaseTime"].toString().toDouble()
    holder.tv_time!!.text = TimeUtils.getDiffTime(obj.toLong())
}

fun onItemHorizontalScrollCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    val data: Result.ItemList = datas.get(position)
    val holder: ItemHorizontalScrollCardHolder = viewHolder as ItemHorizontalScrollCardHolder
    val horizontalScrollCard = data.data as Map<*, *>
    val itemList = horizontalScrollCard["itemList"] as List<Map<*, *>>
    val urlList: MutableList<String>? = ArrayList()
    itemList
            .map { it["data"] as Map<*, *> }
            .forEach { urlList!!.add(it["image"].toString()) }

    val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    holder.recyclerview!!.layoutManager = linearLayout
    val snapHelper: SnapHelper = GravityPagerSnapHelper(Gravity.START)
    snapHelper.attachToRecyclerView(holder.recyclerview)


    val adapter = GalleryAdapter(mContext, urlList!!.toList())
    holder.recyclerview!!.adapter = adapter
    ItemClickSupport.addTo(holder.recyclerview!!)!!.setOnItemClickListener { _, position, _ ->
        val map = itemList[position]
        val data = map["data"] as Map<*, *>
        val url = data["actionUrl"]
        parseUri(mContext, url.toString())
    }
}

fun onItemFollowCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemFollowCardItemHolder = viewHolder as ItemFollowCardItemHolder
    val followCard = data.data as Map<*, *>
    val header = followCard["header"] as Map<*, *>
    val iconType = header["iconType"].toString()

    when (iconType) {
        "square" -> ImageLoad().loadRound(header["icon"].toString(), holder.iv_icon, 5)
        "round" -> ImageLoad().loadCircle(header["icon"].toString(), holder.iv_icon)
        else -> ImageLoad().load(header["icon"].toString(), holder.iv_icon)
    }

    val list = TextUtils.split(header["description"].toString(), "/")
    holder.tv_content!!.text = header["title"].toString() + " / " + list[0]

    val content = followCard["content"] as Map<*, *>
    val datas = content["data"] as Map<*, *>
    val cover = datas["cover"] as Map<*, *>
    val width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
    val height = width * 0.6
    ImageLoad().load(cover["feed"].toString(), holder.iv_cover, width, height.toInt(), 5)

    holder.iv_cover!!.setOnClickListener {
        val title = datas["title"].toString()
        val webUrl = datas["webUrl"] as Map<*, *>
        val url = webUrl["raw"].toString()
        var id = datas["id"]
        parseUri(mContext, parseWebView(title, url))
    }
    holder.tv_time!!.text = TimeUtils.secToTime(datas["duration"].toString().toFloat().toInt())

    holder.tv_title!!.text = datas["title"].toString()
}

fun onItemVideoSmallCardBinder(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemVideoSmallCardHolder = viewHolder as ItemVideoSmallCardHolder
    val videoSmallCard = data.data!! as Map<*, *>
    val cover = videoSmallCard["cover"] as Map<*, *>
    val width = getScreenWidth(mContext) * 0.5
    val height = width * 0.6
    ImageLoad().load(cover["feed"].toString(), holder.iv_cover, width.toInt(), height.toInt(), 5)

    holder.iv_cover!!.setOnClickListener {
        val title = videoSmallCard["title"].toString()
        val webUrl = videoSmallCard["webUrl"] as Map<*, *>
        val url = webUrl["raw"].toString()
        var id = videoSmallCard["id"]
        parseUri(mContext, parseWebView(title, url))
    }
    holder.tv_time!!.text = TimeUtils.secToTime(videoSmallCard["duration"].toString().toFloat().toInt())
    holder.tv_title!!.text = videoSmallCard["title"].toString()
    if (videoSmallCard["author"] != null) {
        val author = videoSmallCard["author"] as Map<*, *>
        holder.tv_content!!.text = "#" + videoSmallCard["category"] + " / " + author["name"].toString().replace(videoSmallCard["category"].toString(), "")
    }
}

fun onItemSquareCardCollectionBinder(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemSquareCardCollectionHolder = viewHolder as ItemSquareCardCollectionHolder
    val squareCardCollection = data.data as Map<*, *>
    val header = squareCardCollection["header"] as Map<*, *>
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


    val actionUrl = header["actionUrl"].toString()

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


    val itemList = squareCardCollection["itemList"] as List<Map<*, *>>
    val urlList: MutableList<String>? = ArrayList<String>()
    for (map in itemList) {
        val type = map["type"]
        val data = map["data"] as Map<*, *>
        if (MyMultiTypeAdapter.ITEM_TYPE.ITEM_FOLLOWCARD.type.hashCode() == type.toString().hashCode()) {
            val content = data["content"] as Map<*, *>
            val datas = content["data"] as Map<*, *>
            val cover = datas["cover"] as Map<*, *>
            urlList!!.add(cover["feed"].toString())
        } else {
            urlList!!.add(data["image"].toString())
        }


    }
    ItemClickSupport.addTo(holder.recyclerview).setOnItemClickListener { _, position, _ ->
        val map = itemList[position]
        val data = map["data"] as Map<*, *>
        if (data["actionUrl"] != null) {
            val actionUrl = data["actionUrl"].toString()
            if (!actionUrl.isEmpty()) {
                parseUri(mContext, actionUrl)
            }
        }
        if (data["content"] != null) {
            val content = data["content"] as Map<*, *>
            if (content["data"] != null) {
                val videodata = content["data"] as Map<*, *>
                val title = videodata["title"].toString()
                if (videodata["webUrl"] != null) {
                    val webUrl = videodata["webUrl"] as Map<*, *>
                    val raw = webUrl["raw"].toString()
                    parseUri(mContext, parseWebView(title, raw))
                }
            }
        }

    }

    val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    holder.recyclerview!!.layoutManager = linearLayout
    val snapHelper: SnapHelper = GravityPagerSnapHelper(Gravity.START)
    snapHelper.attachToRecyclerView(holder.recyclerview)


    val adapter = GalleryAdapter(mContext, urlList!!.toList())
    holder.recyclerview!!.adapter = adapter

}

fun onItemVideoCollectionWithBriefBinder(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemVideoCollectionWithBriefHolder = viewHolder as ItemVideoCollectionWithBriefHolder
    val videoCollectionWithBrief = data.data as Map<*, *>

    val header = videoCollectionWithBrief["header"] as Map<*, *>
    holder.tv_nickname!!.text = header["title"].toString()

    val iconType = header["iconType"].toString()
    when (iconType) {
        "square" -> ImageLoad().loadRound(header["icon"].toString(), holder.iv_icon, 5)
        "round" -> ImageLoad().loadCircle(header["icon"].toString(), holder.iv_icon)
        else -> ImageLoad().load(header["icon"].toString(), holder.iv_icon)
    }
    holder.tv_des!!.text = header["description"].toString()

    val itemList = videoCollectionWithBrief["itemList"] as List<Map<*, *>>
    val urlList: MutableList<VideoCollectionAdapter.VideoCollection>? = ArrayList()
    for (map in itemList) {
        val data = map["data"] as Map<*, *>
        val cover = data["cover"] as Map<*, *>
        val video: VideoCollectionAdapter.VideoCollection = VideoCollectionAdapter.VideoCollection()
        video.icon = cover["feed"].toString()
        video.title = data["title"].toString()
        video.category = data["category"].toString()
        video.duration = data["duration"].toString().toFloat().toInt().toLong()
        urlList!!.add(video)
    }

    val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    holder.recyclerview!!.layoutManager = linearLayout
    val snapHelper: SnapHelper = GravityPagerSnapHelper(Gravity.START)
    snapHelper.attachToRecyclerView(holder.recyclerview)

    ItemClickSupport.addTo(holder.recyclerview).setOnItemClickListener { _, position, _ ->
        val data = itemList[position]["data"] as Map<*, *>
        val title = data["title"].toString()
        if (data["webUrl"] != null) {
            val webUrl = data["webUrl"] as Map<*, *>
            val url = webUrl["raw"].toString()
            parseUri(mContext, parseWebView(title, url))
        }
    }
    val adapter = VideoCollectionAdapter(mContext, urlList!!.toList())
    holder.recyclerview!!.adapter = adapter

}

fun onItemBannerBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemBannerHolder = viewHolder as ItemBannerHolder
    val banner = data.data as Map<*, *>
    val image: String = banner["image"].toString()
    val width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
    val height = width * 0.6
    ImageLoad().load(image, holder.iv_banner, width.toDouble().toInt(), height.toInt(), 5)
    holder.iv_banner!!.setOnClickListener {
        val actionUrl = banner["actionUrl"].toString()
        parseUri(mContext, actionUrl)
    }
}

fun onItemBanner2Bind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemBanner2Holder = viewHolder as ItemBanner2Holder
    val banner = data.data as Map<*, *>
    val image: String = banner["image"].toString()
    val width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
    val height = width * 0.6
    ImageLoad().load(image, holder.iv_banner, width.toDouble().toInt(), height.toDouble().toInt(), 5)

    val label = banner["label"]
    if (label != null) {
        val label = banner["label"] as Map<*, *>
        val text = label["text"]
        if (!text.toString().isEmpty()) {
            holder.tv_banner2!!.text = label["text"].toString()
            holder.tv_banner2!!.visibility = View.VISIBLE
        } else {
            holder.tv_banner2!!.visibility = View.GONE
        }
    } else {
        holder.tv_banner2!!.visibility = View.GONE
    }

    val actionUrl = banner["actionUrl"].toString()
    holder.iv_banner!!.setOnClickListener {
        if (!actionUrl.isEmpty()) {
            parseUri(mContext, actionUrl)
        }
    }


}

fun onItemVideoBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemVideoItemHolder = viewHolder as ItemVideoItemHolder
    val video = data.data as Map<*, *>
    if (video["author"] != null) {
        val author = video["author"] as Map<*, *>
        ImageLoad().loadCircle(author["icon"].toString(), holder.iv_icon)
        holder.tv_content!!.text = author["name"].toString() + " / #" + video["category"]
    } else if (video["tags"] != null) {
        val tags = video["tags"] as List<Map<*, *>>
        ImageLoad().loadRound(tags[0]["headerImage"].toString(), holder.iv_icon, 5)
        holder.tv_content!!.text = "#" + tags[0]["name"].toString() + "#"
    }

    val cover = video["cover"] as Map<*, *>
    val width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 20f)
    val height = width * 0.6
    ImageLoad().load(cover["feed"].toString(), holder.iv_cover, width, height.toDouble().toInt(), 5)

    holder.iv_cover!!.setOnClickListener {
        val title = video["title"].toString()
        val webUrl = video["webUrl"] as Map<*, *>
        val url = webUrl["raw"].toString()
        var id = video["id"]
        parseUri(mContext, parseWebView(title, url))
    }
    holder.tv_time!!.text = TimeUtils.secToTime(video["duration"].toString().toFloat().toInt())

    holder.tv_title!!.text = video["title"].toString()

}

fun onItemVideoCollectionOfBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemVideoCollectionOfHolder = viewHolder as ItemVideoCollectionOfHolder
    val videoCollectionOfHorizontalScrollCard = data.data as Map<*, *>

    val header = videoCollectionOfHorizontalScrollCard["header"] as Map<*, *>
    holder.tv_videocollection_of_title!!.text = header["title"].toString()
    val actionUrl = header["actionUrl"]

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

    val itemList = videoCollectionOfHorizontalScrollCard["itemList"] as List<Map<*, *>>
    val urlList: MutableList<VideoCollectionAdapter.VideoCollection>? = ArrayList()
    for (map in itemList) {
        val data = map["data"] as Map<*, *>
        val cover = data["cover"] as Map<*, *>
        val video: VideoCollectionAdapter.VideoCollection = VideoCollectionAdapter.VideoCollection()
        video.icon = cover["feed"].toString()
        video.title = data["title"].toString()
        video.category = data["category"].toString()
        video.duration = data["duration"].toString().toFloat().toInt().toLong()
        urlList!!.add(video)
    }

    val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    holder.recyclerview_videocollection_of!!.layoutManager = linearLayout
    val snapHelper: SnapHelper = GravityPagerSnapHelper(Gravity.START)
    snapHelper.attachToRecyclerView(holder.recyclerview_videocollection_of)
    ItemClickSupport.addTo(holder.recyclerview_videocollection_of).setOnItemClickListener { _, position, _ ->
        val data = itemList[position]["data"] as Map<*, *>
        val title = data["title"].toString()
        val webUrl = data["webUrl"] as Map<*, *>
        val url = webUrl["raw"].toString()
        parseUri(mContext, parseWebView(title, url))
    }

    val adapter = VideoCollectionAdapter(mContext, urlList!!.toList())
    holder.recyclerview_videocollection_of!!.adapter = adapter
}