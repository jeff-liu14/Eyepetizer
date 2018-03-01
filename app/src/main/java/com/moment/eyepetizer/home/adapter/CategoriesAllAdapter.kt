package com.moment.eyepetizer.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.moment.eyepetizer.R
import com.moment.eyepetizer.net.entity.Result
import com.moment.eyepetizer.utils.*
import kotlinx.android.synthetic.main.rectangle_card_item.view.*
import kotlinx.android.synthetic.main.square_card_item.view.*

/**
 * Created by moment on 2018/2/12.
 */

class CategoriesAllAdapter(datas: ArrayList<Result.ItemList>, var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var datas: ArrayList<Result.ItemList> = datas
    private var mContext: Context = context

    fun clearAll() = this.datas.clear()

    fun addAll(data: ArrayList<Result.ItemList>?) {
        if (data == null) {
            return
        }
        this.datas.addAll(data)
    }

    enum class ITEM_TYPE(val type: String) {
        ITEM_SQUARECARD("squareCard"),
        ITEM_RECTANGLECARD("rectangleCard")
    }

    class ItemSquareCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_square: ImageView? = itemView.iv_square
        var tv_square: TextView? = itemView.tv_square
        var root: LinearLayout? = itemView.ll_square_root
    }

    class ItemRectangleCardItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_rectangle: ImageView? = itemView.iv_rectangle
        var tv_rectangle: TextView? = itemView.tv_rectangle
        var root: LinearLayout? = itemView.ll_rectangle_root
    }


    fun onItemSquareCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas[position]
        var holder: ItemSquareCardItemHolder = viewHolder as ItemSquareCardItemHolder
        var squareCard: Map<String, Object> = data.data as Map<String, Object>
        var dataType = squareCard["dataType"].toString()
        var width = (getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 8f)) / 2
        if ("SquareCard" == dataType) {
            ImageLoad().load(squareCard["image"].toString(), holder.iv_square, width, width)
        }
        var actionUrl = squareCard["actionUrl"].toString()
        holder.root!!.setOnClickListener {
            if (actionUrl != null) {
                parseUri(mContext, actionUrl)
            }
        }
        var lp = holder.tv_square!!.layoutParams
        lp.width = width
        lp.height = width
        holder.tv_square!!.layoutParams = lp
        var title = squareCard["title"].toString()
        if (!TextUtils.isEmpty(title)) {
            holder.tv_square!!.text = squareCard["title"].toString()
            holder.tv_square!!.visibility = View.VISIBLE
        } else {
            holder.tv_square!!.visibility = View.GONE
        }

    }


    fun onItemRectangleCardBind(mContext: Context, datas: ArrayList<Result.ItemList>, viewHolder: RecyclerView.ViewHolder, position: Int) {
        var data: Result.ItemList = datas[position]
        var holder: ItemRectangleCardItemHolder = viewHolder as ItemRectangleCardItemHolder
        var rectangleCard: Map<String, Object> = data.data as Map<String, Object>
        var dataType = rectangleCard["dataType"].toString()
        if ("RectangleCard" == dataType) {
            var width = getScreenWidth(mContext) - DensityUtil.dip2px(mContext, 4f)
            ImageLoad().load(rectangleCard["image"].toString(), holder.iv_rectangle, width, width / 2)
        }
        var actionUrl = rectangleCard["actionUrl"].toString()
        holder.root!!.setOnClickListener {
            if (actionUrl != null) {
                parseUri(mContext, actionUrl)
            }
        }
        holder.tv_rectangle!!.text = rectangleCard["title"].toString()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is ItemSquareCardItemHolder -> onItemSquareCardBind(mContext, datas, holder, position)
            is ItemRectangleCardItemHolder -> onItemRectangleCardBind(mContext, datas, holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int = getMultiType(position, datas)


    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? =
            when (viewType) {
                ITEM_TYPE.ITEM_SQUARECARD.type.hashCode() -> {
                    val view = LayoutInflater.from(parent!!.context).inflate(R.layout.square_card_item, parent, false)
                    ItemSquareCardItemHolder(view)
                }
                ITEM_TYPE.ITEM_RECTANGLECARD.type.hashCode() -> {
                    val view = LayoutInflater.from(parent!!.context).inflate(R.layout.rectangle_card_item, parent, false)
                    ItemRectangleCardItemHolder(view)
                }
                else -> {
                    var view = LayoutInflater.from(parent!!.context).inflate(R.layout.square_card_item, parent, false)
                    view.visibility = View.GONE
                    ItemEmptyHolder(view)
                }
            }


}