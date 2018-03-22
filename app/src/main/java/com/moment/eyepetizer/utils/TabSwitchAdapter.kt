package com.moment.eyepetizer.utils

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.tab_switch_item.view.*
import android.view.LayoutInflater
import com.moment.eyepetizer.R
import com.moment.eyepetizer.net.entity.Categories
import java.util.*


/**
 * Created by moment on 2018/3/7.
 */
class TabSwitchAdapter(var data: ArrayList<Categories>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {
    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(datas, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        datas.removeAt(position)
        notifyItemRemoved(position)
    }

    private var datas: ArrayList<Categories> = data

    fun clearAll() = this.datas.clear()

    fun addAll(data: ArrayList<Categories>?) {
        if (data == null) {
            return
        }
        this.datas.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.tab_switch_item, parent, false)
        return ItemTabSwitchHolder(view)
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?, position: Int) {
        var categorie = datas[position]
        var holder = viewHolder as ItemTabSwitchHolder
        holder.tv_tab_title!!.text = "#" + categorie.name
        holder.tv_tab_des!!.text = categorie.description
        ImageLoad().loadRound(categorie.headerImage!!, holder.iv_tab_switch_icon, 2)
    }

    override fun getItemCount(): Int {
        return datas.size
    }


    class ItemTabSwitchHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemTouchHelperViewHolder {
        override fun onItemSelected() {

        }

        override fun onItemClear() {
        }

        var iv_tab_switch_icon: ImageView? = itemView.iv_tab_switch_icon
        var tv_tab_title: TextView? = itemView.tv_tab_title
        var tv_tab_des: TextView? = itemView.tv_tab_des
    }


}