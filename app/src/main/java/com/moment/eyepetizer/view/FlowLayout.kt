package com.moment.eyepetizer.view

import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.moment.eyepetizer.R
import com.moment.eyepetizer.utils.DensityUtil

import java.util.ArrayList

class FlowLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private var clickListener: OnFlowItemClickListener? = null

    /**
     * 存储所有的View，按行记录
     */
    private val mAllViews = ArrayList<List<View>>()
    /**
     * 记录每一行的最大高度
     */
    private val mLineHeight = ArrayList<Int>()

    override fun generateLayoutParams(
            p: ViewGroup.LayoutParams): ViewGroup.LayoutParams = ViewGroup.MarginLayoutParams(p)

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams =
            ViewGroup.MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams =
            ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)

    fun setOnFlowItemClickListener(clickListener: OnFlowItemClickListener) {
        this.clickListener = clickListener
    }

    interface OnFlowItemClickListener {
        fun onItemClick(txt: String)
    }

    private fun addText(view: TextView) {
        view.setOnClickListener {
            if (clickListener != null) {
                val txt = view.text.toString().trim { it <= ' ' }
                clickListener!!.onItemClick(txt)
            }
        }
        addView(view)
    }

    open fun setTagView(context: Context, stringList: List<String>) {
        removeAllViews()
        for (i in stringList.indices) {
            if (!TextUtils.isEmpty(stringList[i].trim { it <= ' ' })) {
                val textView = TextView(context)
                textView.text = stringList[i]
                textView.setTextColor(ContextCompat.getColor(context, R.color.white))
                textView.textSize = 14f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    textView.background = ContextCompat.getDrawable(context, R.drawable.btn_shap_txt)
                } else {
                    textView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.btn_shap_txt))
                }
                val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                lp.leftMargin = DensityUtil.dip2px(context, 6f)
                lp.rightMargin = DensityUtil.dip2px(context, 6f)
                lp.bottomMargin = DensityUtil.dip2px(context, 4f)
                lp.topMargin = DensityUtil.dip2px(context, 4f)
                textView.layoutParams = lp
                textView.gravity = Gravity.CENTER_VERTICAL
                textView.setPadding(DensityUtil.dip2px(context, 8f), DensityUtil.dip2px(context, 4f),
                        DensityUtil.dip2px(context, 8f), DensityUtil.dip2px(context, 4f))
                addText(textView)
            }
        }
    }

    /**
     * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 获得它的父容器为它设置的测量模式和大小
        val sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        val modeWidth = View.MeasureSpec.getMode(widthMeasureSpec)
        val modeHeight = View.MeasureSpec.getMode(heightMeasureSpec)
        parentWidth = measureSize(widthMeasureSpec, DensityUtil.dip2px(context, 240f))

        Log.e(TAG, sizeWidth.toString() + "," + sizeHeight)

        // 如果是warp_content情况下，记录宽和高
        var width = 0
        var height = 0
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        var lineWidth = 0
        /**
         * 每一行的高度，累加至height
         */
        var lineHeight = 0

        val cCount = childCount

        // 遍历每个子元素
        for (i in 0 until cCount) {
            val child = getChildAt(i)
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            // 得到child的lp
            val lp = child
                    .layoutParams as ViewGroup.MarginLayoutParams
            // 当前子空间实际占据的宽度
            val childWidth = (child.measuredWidth + lp.leftMargin
                    + lp.rightMargin)
            // 当前子空间实际占据的高度
            val childHeight = (child.measuredHeight + lp.topMargin
                    + lp.bottomMargin)
            /**
             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
             */
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(lineWidth, childWidth)// 取最大的
                lineWidth = childWidth // 重新开启新行，开始记录
                // 叠加当前高度，
                height += lineHeight
                // 开启记录下一行的高度
                lineHeight = childHeight
            } else
            // 否则累加值lineWidth,lineHeight取最大高度
            {
                lineWidth += childWidth
                lineHeight = Math.max(lineHeight, childHeight)
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth)
                height += lineHeight
            }

        }
        setMeasuredDimension(if (modeWidth == View.MeasureSpec.EXACTLY)
            sizeWidth
        else
            width, if (modeHeight == View.MeasureSpec.EXACTLY)
            sizeHeight
        else
            height)

    }

    private fun measureSize(measureSpec: Int, defaultSize: Int): Int {
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)
        var result = defaultSize

        if (mode == View.MeasureSpec.EXACTLY) {
            result = size
        } else if (mode == View.MeasureSpec.AT_MOST) {
            result = Math.max(size, result)
        }

        return result
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mAllViews.clear()
        mLineHeight.clear()

        val width = width

        var lineWidth = 0
        var lineHeight = 0
        // 存储每一行所有的childView
        var lineViews: MutableList<View> = ArrayList()
        val cCount = childCount
        // 遍历所有的孩子
        for (i in 0 until cCount) {
            val child = getChildAt(i)
            val lp = child
                    .layoutParams as ViewGroup.MarginLayoutParams
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            // 如果已经需要换行
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight)
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews)
                lineWidth = 0// 重置行宽
                lineViews = ArrayList()
            }
            /**
             * 如果不需要换行，则累加
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin)
            lineViews.add(child)
        }
        // 记录最后一行
        mLineHeight.add(lineHeight)
        mAllViews.add(lineViews)

        var left = 0
        var top = 0
        // 得到总行数
        val lineNums = mAllViews.size
        for (i in 0 until lineNums) {
            // 每一行的所有的views
            lineViews = mAllViews[i] as MutableList<View>
            // 当前行的最大高度
            lineHeight = mLineHeight[i]

//            Log.e(TAG, "第" + i + "行 ：" + lineViews.size + " , " + lineViews)
//            Log.e(TAG, "第" + i + "行， ：" + lineHeight)

            // 遍历当前行所有的View
            for (j in lineViews.indices) {
                val child = lineViews[j]
                if (child.visibility == View.GONE) {
                    continue
                }
                val lp = child
                        .layoutParams as ViewGroup.MarginLayoutParams

                //计算childView的left,top,right,bottom
                val lc = left + lp.leftMargin
                val tc = top + lp.topMargin
                val rc = lc + child.measuredWidth
                val bc = tc + child.measuredHeight

//                Log.e(TAG, child.toString() + " , l = " + lc + " , t = " + t + " , r ="
//                        + rc + " , b = " + bc)

                child.layout(lc, tc, rc, bc)

                left += (child.measuredWidth + lp.rightMargin
                        + lp.leftMargin)

            }
            adjustLine(top, left, lineViews)
            left = 0
            top += lineHeight

        }

    }

    var parentWidth: Int = 0

    /* 调整一行，让这一行的子布局水平居中 */
    private fun adjustLine(top: Int, lineWidth: Int, lineViews: MutableList<View>) {
        var left = (parentWidth - lineWidth) / 2
        var top = top
        for (j in lineViews.indices) {
            val child = lineViews[j]
            if (child.visibility == View.GONE) {
                continue
            }
            val lp = child
                    .layoutParams as ViewGroup.MarginLayoutParams

            //计算childView的left,top,right,bottom
            val lc = left + lp.leftMargin
            val tc = top + lp.topMargin
            val rc = lc + child.measuredWidth
            val bc = tc + child.measuredHeight


            child.layout(lc, tc, rc, bc)

            left += (child.measuredWidth + lp.rightMargin
                    + lp.leftMargin)
        }
    }

    companion object {

        private val TAG = "FlowLayout"
    }
}
