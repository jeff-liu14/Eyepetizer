package com.moment.eyepetizer.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.moment.eyepetizer.R
import kotlinx.android.synthetic.main.banner2_item.view.*
import kotlinx.android.synthetic.main.banner_item.view.*
import kotlinx.android.synthetic.main.brief_card_item.view.*
import kotlinx.android.synthetic.main.dynamic_infocard_item.view.*
import kotlinx.android.synthetic.main.followcard_item.view.*
import kotlinx.android.synthetic.main.horizontal_scrollcard_item.view.*
import kotlinx.android.synthetic.main.squarecard_collection_item.view.*
import kotlinx.android.synthetic.main.text_card_item.view.*
import kotlinx.android.synthetic.main.text_footer_item.view.*
import kotlinx.android.synthetic.main.text_header_item.view.*
import kotlinx.android.synthetic.main.video_smallcard_item.view.*
import kotlinx.android.synthetic.main.videocollection_ofhoriscroll_item.view.*
import kotlinx.android.synthetic.main.videocollection_withbrief_item.view.*

/**
 * Created by moment on 2018/2/9.
 */

fun createMyViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
    when (viewType) {
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_TEXTCARD.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.text_card_item, viewGroup, false)
            return ItemTextCardItemHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_BRIEFCARD.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.brief_card_item, viewGroup, false)
            return ItemBriefCardItemHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_DYNAMIC_INFOCARD.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.dynamic_infocard_item, viewGroup, false)
            return ItemDynamicInfoCardItemHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_HORICONTAL_SCROLLCARD.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.horizontal_scrollcard_item, viewGroup, false)
            return ItemHorizontalScrollCardHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_FOLLOWCARD.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.followcard_item, viewGroup, false)
            return ItemFollowCardItemHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_VIDEOSMALLCARD.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.video_smallcard_item, viewGroup, false)
            return ItemVideoSmallCardHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_SQUARECARD_COLLECTION.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.squarecard_collection_item, viewGroup, false)
            return ItemSquareCardCollectionHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_VIDEOCOLLECTION_WITHBRIEF.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.videocollection_withbrief_item, viewGroup, false)
            return ItemVideoCollectionWithBriefHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_BANNER.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.banner_item, viewGroup, false)
            return ItemBannerHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_BANNER2.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.banner2_item, viewGroup, false)
            return ItemBanner2Holder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_VIDEO.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.followcard_item, viewGroup, false)
            return ItemVideoItemHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_VIDEOCOLLECTION_OFHORISCROLLCARD.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.videocollection_ofhoriscroll_item, viewGroup, false)
            return ItemVideoCollectionOfHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_TEXTHEADER.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.text_header_item, viewGroup, false)
            return ItemTextHeaderItemHolder(view)
        }
        MyMultiTypeAdapter.ITEM_TYPE.ITEM_TEXTFOOTER.type.hashCode() -> {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.text_footer_item, viewGroup, false)
            return ItemTextFooterItemHolder(view)
        }
        else -> {
            var view = LayoutInflater.from(viewGroup.context).inflate(R.layout.text_card_item, viewGroup, false)
            view.visibility = View.GONE
            return ItemEmptyHolder(view)
        }
    }
}

class ItemTextCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv_title: TextView? = itemView.tv_text_card_title
    var ll_header5: LinearLayout? = itemView.ll_header5
    var rl_footer2: RelativeLayout? = itemView.rl_footer2
    var tv_footer: TextView? = itemView.tv_footer
    var iv_more_header: ImageView? = itemView.iv_more_header
    var iv_more: ImageView? = itemView.iv_more
}

class ItemTextHeaderItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv_title: TextView? = itemView.tv_text_header_title
    var iv_text_header_more: ImageView? = itemView.iv_text_header_more
}

class ItemTextFooterItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv_text_footer: TextView? = itemView.tv_text_footer
    var iv_footer_more: ImageView? = itemView.iv_footer_more
}


class ItemBriefCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var image: ImageView? = itemView.image
    var tv_title: TextView? = itemView.tv_brief_card_title
    var tv_content: TextView? = itemView.tv_brief_card_content
    var rl_brief_root: RelativeLayout = itemView.rl_brief_root
}

class ItemDynamicInfoCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

class ItemHorizontalScrollCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var recyclerview: RecyclerView? = itemView.recyclerview_horizontal_scrollcard
}

class ItemFollowCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var iv_cover: ImageView? = itemView.iv_followcard_cover
    var tv_time: TextView? = itemView.tv_followcard_time
    var iv_icon: ImageView? = itemView.iv_followcard_icon
    var tv_title: TextView? = itemView.tv_followcard_title
    var tv_content: TextView? = itemView.tv_followcard_content
}

class ItemVideoSmallCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var iv_cover: ImageView? = itemView.iv_cover
    var tv_time: TextView? = itemView.tv_time
    var tv_title: TextView? = itemView.tv_smallcard_title
    var tv_content: TextView? = itemView.tv_smallcard_content
}

class ItemSquareCardCollectionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var recyclerview: RecyclerView? = itemView.recyclerview_squarecard_collection
    var tv_title: TextView? = itemView.tv_squarecard_collection_title
    var iv_squarecard_more: ImageView? = itemView.iv_squarecard_more
    var tv_squarecard_collection_sub_title: TextView? = itemView.tv_squarecard_collection_sub_title
}

class ItemVideoCollectionWithBriefHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var iv_icon: ImageView? = itemView.iv_videocollection_icon
    var tv_nickname: TextView? = itemView.tv_videocollection_nickname
    var tv_des: TextView? = itemView.tv_videocollection_des
    var recyclerview: RecyclerView? = itemView.recyclerview_videocollection
}

class ItemBannerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var iv_banner: ImageView? = itemView.iv_banner
}

class ItemBanner2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var iv_banner: ImageView? = itemView.iv_banner2
    var tv_banner2: TextView? = itemView.tv_banner2
}

class ItemVideoItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var iv_cover: ImageView? = itemView.iv_followcard_cover
    var tv_time: TextView? = itemView.tv_followcard_time
    var iv_icon: ImageView? = itemView.iv_followcard_icon
    var tv_title: TextView? = itemView.tv_followcard_title
    var tv_content: TextView? = itemView.tv_followcard_content
}

class ItemVideoCollectionOfHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv_videocollection_of_title: TextView? = itemView.tv_videocollection_of_title
    var iv_videocollection_of_header: ImageView? = itemView.iv_videocollection_of_header
    var recyclerview_videocollection_of: RecyclerView? = itemView.recyclerview_videocollection_of
}

class ItemEmptyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    init {

    }
}