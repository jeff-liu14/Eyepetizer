package com.moment.eyepetizer.home.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by moment on 2018/2/12.
 */

public class ItemDivider extends RecyclerView.ItemDecoration {
    // 构造方法,可以在这里做一些初始化,比如指定画笔颜色什么的
    public ItemDivider() {
    }

    /**
     * 指定item之间的间距(就是指定分割线的宽度)   回调顺序 1
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    /**
     * 在item 绘制之前调用(就是绘制在 item 的底层)  回调顺序 2
     * 一般分割线在这里绘制
     * 看到canvas,对自定义控件有一定了解的话,就能想到为什么说给RecyclerView设置分割线更灵活了
     *
     * @param c      Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state  The current state of RecyclerView
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    /**
     * 在item 绘制之后调用(就是绘制在 item 的上层)  回调顺序 3
     * 也可以在这里绘制分割线,和上面的方法 二选一
     *
     * @param c      Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state  The current state of RecyclerView
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
