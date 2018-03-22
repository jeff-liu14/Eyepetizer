package com.moment.eyepetizer.utils

/**
 * Created by moment on 2018/3/7.
 */

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)
}