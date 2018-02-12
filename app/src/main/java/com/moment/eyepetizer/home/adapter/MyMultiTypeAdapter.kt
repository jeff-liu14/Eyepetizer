package com.moment.eyepetizer.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView


import com.moment.eyepetizer.net.entity.Result
import android.view.*
import com.moment.eyepetizer.utils.getMultiType
import kotlin.collections.ArrayList

/**
 * Created by moment on 2017/12/11.
 */

class MyMultiTypeAdapter(datas: ArrayList<Result.ItemList>, var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var datas: ArrayList<Result.ItemList> = datas
    private var mContext: Context = context

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
        ITEM_VIDEO("video"),
        ITEM_VIDEOCOLLECTION_OFHORISCROLLCARD("videoCollectionOfHorizontalScrollCard"),
        ITEM_TEXTHEADER("textHeader"),
        ITEM_TEXTFOOTER("textFooter")
    }


    fun clearAll() = this.datas.clear()

    fun addAll(data: ArrayList<Result.ItemList>?) {
        if (data == null) {
            return
        }
        this.datas.addAll(data)
    }

    //创建新View，被LayoutManager所调用
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? =
            createMyViewHolder(viewGroup, viewType)


    //将数据与界面进行绑定的操作
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) =
            bindViewHolder(mContext, datas, viewHolder, position)

    override fun getItemViewType(position: Int): Int = getMultiType(position, datas)

    //获取数据的数量
    override fun getItemCount(): Int = datas.size

}
