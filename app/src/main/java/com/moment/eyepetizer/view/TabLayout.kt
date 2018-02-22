package com.moment.eyepetizer.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView

import com.moment.eyepetizer.R

import java.util.ArrayList
import java.util.Collections

/**
 * Created by moment on 2018/2/22.
 * FlycoTabLayout 仿写，并提取部分功能原地址链接：
 * https://github.com/H07000223/FlycoTabLayout
 */

open class TabLayout(context: Context, attrs: AttributeSet) : HorizontalScrollView(context, attrs), ViewPager.OnPageChangeListener {
    private var mContext: Context? = null
    private var textNormalSize: Float = 0.toFloat()
    private var textSelectSize: Float = 0.toFloat()
    private var textNormalColor: Int = 0
    private var textSelectColor: Int = 0
    private var underlineHeight: Float = 0.toFloat()
    private var underlineColor: Int = 0
    private var indicatorHeight: Float = 0.toFloat()
    private var indicatorWidth: Float = 0.toFloat()
    private var indicatorSpacing: Float = 0.toFloat()
    private var isTextBold: Boolean = false
    private var tabPadding: Float = 0.toFloat()
    private var indicatorColor: Int = 0
    private var tabSpaceEqual: Boolean = false


    private var selectListener: OnTagSelectListener? = null
    private var mTabsContainer: LinearLayout? = null
    private var mCurrentTab = 0
    private var mViewPager: ViewPager? = null
    var tabCount: Int = 0
        private set
    private var mTitles: ArrayList<String>? = null

    private val mRectPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val mIndicatorDrawable = GradientDrawable()
    /**
     * indicator
     */

    private val mIndicatorMarginLeft = 0f
    private val mIndicatorMarginTop = 0f
    private val mIndicatorMarginRight = 0f
    /**
     * 用于绘制显示器
     */
    private val mIndicatorRect = Rect()

    private var mCurrentPositionOffset: Float = 0.toFloat()

    /**
     * 用于实现滚动居中
     */
    private val mTabRect = Rect()
    private var mLastScrollX: Int = 0

    init {
        initResource(context, attrs)
    }


    private fun initResource(context: Context, attrs: AttributeSet) {
        mContext = context
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTabLayout)
        textNormalSize = typedArray.getDimension(R.styleable.MyTabLayout_textNormalSize, sp2px(context, 14f).toFloat())
        textSelectSize = typedArray.getDimension(R.styleable.MyTabLayout_textSelectSize, textNormalSize)
        textNormalColor = typedArray.getColor(R.styleable.MyTabLayout_textNormalColor, Color.GRAY)
        textSelectColor = typedArray.getColor(R.styleable.MyTabLayout_textSelectColor, Color.BLACK)
        underlineHeight = typedArray.getDimension(R.styleable.MyTabLayout_underLineHeight, dp2px(context, 0.5f).toFloat())
        indicatorWidth = typedArray.getDimension(R.styleable.MyTabLayout_indicatorWidth, -1f)
        indicatorHeight = typedArray.getDimension(R.styleable.MyTabLayout_indicatorHeight, dp2px(context, 3f).toFloat())
        indicatorSpacing = typedArray.getDimension(R.styleable.MyTabLayout_indicatorSpacing, dp2px(context, 6f).toFloat())
        isTextBold = typedArray.getBoolean(R.styleable.MyTabLayout_isTextBold, true)
        underlineColor = typedArray.getColor(R.styleable.MyTabLayout_underlineColor, Color.parseColor("#EEEEEE"))
        indicatorColor = typedArray.getColor(R.styleable.MyTabLayout_indicatorColor, Color.BLACK)
        tabSpaceEqual = typedArray.getBoolean(R.styleable.MyTabLayout_tabSpaceEqual, false)
        tabPadding = typedArray.getDimension(R.styleable.MyTabLayout_tabPadding, (if (tabSpaceEqual) dp2px(context, 5f) else dp2px(context, 20f)).toFloat())
        typedArray.recycle()

        mRectPaint.color = underlineColor
        mIndicatorDrawable.setColor(indicatorColor)
        mTabsContainer = LinearLayout(context)
        val lp = ViewGroup.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        mTabsContainer!!.layoutParams = lp
        mTabsContainer!!.gravity = Gravity.CENTER_VERTICAL
        isFillViewport = true
        setWillNotDraw(false)
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        addView(mTabsContainer)

    }

    //setter and getter
    fun setCurrentTab(currentTab: Int) {
        this.mCurrentTab = currentTab
        mViewPager!!.currentItem = currentTab

    }

    fun setCurrentTab(currentTab: Int, smoothScroll: Boolean) {
        this.mCurrentTab = currentTab
        mViewPager!!.setCurrentItem(currentTab, smoothScroll)
    }

    fun addTab(position: Int, title: String?, textView: TextView?) {
        if (textView != null) {
            if (title != null) {
                textView.gravity = Gravity.CENTER
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textNormalSize)
                textView.setTextColor(textNormalColor)
                textView.text = title
                textView.setOnClickListener { v ->
                    val position = mTabsContainer!!.indexOfChild(v)
                    if (position != -1) {
                        if (mViewPager!!.currentItem != position) {
                            mViewPager!!.currentItem = position
                        } else {
                            if (selectListener != null) {
                                selectListener!!.onTagSelected(position, if (mTitles != null) mTitles!![position] else mViewPager!!.adapter.getPageTitle(position).toString())
                            }
                        }
                    }
                }
            }
        }
        val lp = if (tabSpaceEqual)
            LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f)
        else
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        lp.gravity = Gravity.CENTER
        mTabsContainer!!.addView(textView, position, lp)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val height = height
        val paddingLeft = paddingLeft
        var top = 0
        var bottom = height

        if (mTabsContainer != null && mTabsContainer!!.getChildAt(mCurrentTab) != null) {
            top = mTabsContainer!!.getChildAt(mCurrentTab).top
            bottom = mTabsContainer!!.getChildAt(mCurrentTab).bottom
        }

        val tabHeight = top + bottom

        canvas.drawRect(paddingLeft.toFloat(), height - underlineHeight, (mTabsContainer!!.width + paddingLeft).toFloat(), height.toFloat(), mRectPaint)

        calcIndicatorRect()

        if (indicatorHeight > 0 && indicatorWidth > 0) {
            mIndicatorDrawable.setBounds(paddingLeft + mIndicatorMarginLeft.toInt() + mIndicatorRect.left,
                    tabHeight - tabPadding.toInt() * 2 + indicatorSpacing.toInt(),
                    paddingLeft + mIndicatorRect.right - mIndicatorMarginRight.toInt(),
                    tabHeight - tabPadding.toInt() * 2 + indicatorSpacing.toInt() + indicatorHeight.toInt())

            mIndicatorDrawable.draw(canvas)
        } else {
            mIndicatorDrawable.setBounds(paddingLeft + mIndicatorMarginLeft.toInt() + mIndicatorRect.left,
                    height - indicatorHeight.toInt(),
                    paddingLeft + mIndicatorRect.right - mIndicatorMarginRight.toInt(),
                    height)
            mIndicatorDrawable.draw(canvas)
        }
    }

    private fun calcIndicatorRect() {
        val currentTabView = mTabsContainer!!.getChildAt(this.mCurrentTab) ?: return
        var left = currentTabView.left.toFloat()
        var right = currentTabView.right.toFloat()

        if (this.mCurrentTab < tabCount - 1) {
            val nextTabView = mTabsContainer!!.getChildAt(this.mCurrentTab + 1)
            val nextTabLeft = nextTabView.left.toFloat()
            val nextTabRight = nextTabView.right.toFloat()

            left += mCurrentPositionOffset * (nextTabLeft - left)
            right += mCurrentPositionOffset * (nextTabRight - right)
        }

        mIndicatorRect.left = left.toInt()
        mIndicatorRect.right = right.toInt()

        mTabRect.left = left.toInt()
        mTabRect.right = right.toInt()

        if (indicatorWidth >= 0) {
            //indicatorWidth大于0时,圆角矩形以及三角形
            var indicatorLeft = currentTabView.left + (currentTabView.width - indicatorWidth) / 2

            if (this.mCurrentTab < tabCount - 1) {
                val nextTab = mTabsContainer!!.getChildAt(this.mCurrentTab + 1)
                indicatorLeft += mCurrentPositionOffset * (currentTabView.width / 2 + nextTab.width / 2)
            }

            mIndicatorRect.left = indicatorLeft.toInt()
            mIndicatorRect.right = (mIndicatorRect.left + indicatorWidth).toInt()
        }
    }

    /**
     * 关联ViewPager,用于不想在ViewPager适配器中设置titles数据的情况
     */
    fun setViewPager(vp: ViewPager?, titles: Array<String>) {
        if (vp == null || vp.adapter == null) {
            throw IllegalStateException("ViewPager or ViewPager adapter can not be NULL !")
        }

        if (titles == null || titles.size == 0) {
            throw IllegalStateException("Titles can not be EMPTY !")
        }

        if (titles.size != vp.adapter.count) {
            throw IllegalStateException("Titles length must be the same as the page count !")
        }

        this.mViewPager = vp
        mTitles = ArrayList()
        Collections.addAll(mTitles!!, *titles)

        this.mViewPager!!.removeOnPageChangeListener(this)
        this.mViewPager!!.addOnPageChangeListener(this)
        notifyDataSetChanged()
    }

    /**
     * 关联ViewPager,用于连适配器都不想自己实例化的情况
     */
    fun setViewPager(vp: ViewPager?, titles: Array<String>?, fa: FragmentActivity, fragments: ArrayList<Fragment>) {
        if (vp == null) {
            throw IllegalStateException("ViewPager can not be NULL !")
        }

        if (titles == null || titles.isEmpty()) {
            throw IllegalStateException("Titles can not be EMPTY !")
        }

        this.mViewPager = vp
        this.mViewPager!!.adapter = InnerPagerAdapter(fa.supportFragmentManager, fragments, titles)

        this.mViewPager!!.removeOnPageChangeListener(this)
        this.mViewPager!!.addOnPageChangeListener(this)
        notifyDataSetChanged()
    }

    /**
     * 更新数据
     */
    private fun notifyDataSetChanged() {
        mTabsContainer!!.removeAllViews()
        this.tabCount = if (mTitles == null) mViewPager!!.adapter.count else mTitles!!.size
        var tabView: TextView
        for (i in 0 until tabCount) {
            tabView = TextView(mContext)
            val pageTitle = if (mTitles == null) mViewPager!!.adapter.getPageTitle(i) else mTitles!![i]
            addTab(i, pageTitle.toString(), tabView)
        }

        updateTabStyles()
    }

    private fun updateTabStyles() {
        for (i in 0 until tabCount) {
            val v = mTabsContainer!!.getChildAt(i)
            val tv_tab_title = v as TextView
            if (tv_tab_title != null) {
                tv_tab_title.setTextColor(if (i == mCurrentTab) textSelectColor else textNormalColor)
                tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, if (i == mCurrentTab) textSelectSize else textNormalSize)
                tv_tab_title.setPadding(tabPadding.toInt(), tabPadding.toInt(), tabPadding.toInt(), tabPadding.toInt())
                if (isTextBold) {
                    tv_tab_title.paint.isFakeBoldText = i == mCurrentTab
                }

            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        this.mCurrentTab = position
        this.mCurrentPositionOffset = positionOffset
        scrollToCurrentTab()
        invalidate()
    }

    /**
     * HorizontalScrollView滚到当前tab,并且居中显示
     */
    private fun scrollToCurrentTab() {
        if (tabCount <= 0) {
            return
        }

        val offset = (mCurrentPositionOffset * mTabsContainer!!.getChildAt(mCurrentTab).width).toInt()
        /**当前Tab的left+当前Tab的Width乘以positionOffset */
        var newScrollX = mTabsContainer!!.getChildAt(mCurrentTab).left + offset

        if (mCurrentTab > 0 || offset > 0) {
            /**HorizontalScrollView移动到当前tab,并居中 */
            newScrollX -= width / 2 - paddingLeft
            calcIndicatorRect()
            newScrollX += (mTabRect.right - mTabRect.left) / 2
        }

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX
            /** scrollTo（int x,int y）:x,y代表的不是坐标点,而是偏移量
             * x:表示离起始位置的x水平方向的偏移量
             * y:表示离起始位置的y垂直方向的偏移量
             */
            scrollTo(newScrollX, 0)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("instanceState", super.onSaveInstanceState())
        bundle.putInt("mCurrentTab", mCurrentTab)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var state = state
        if (state is Bundle) {
            val bundle = state as Bundle?
            mCurrentTab = bundle!!.getInt("mCurrentTab")
            state = bundle.getParcelable("instanceState")
            if (mCurrentTab != 0 && mTabsContainer!!.childCount > 0) {
                updateTabSelection(mCurrentTab)
                scrollToCurrentTab()
            }
        }
        super.onRestoreInstanceState(state)
    }

    private fun updateTabSelection(position: Int) {
        for (i in 0 until tabCount) {
            val tabView = mTabsContainer!!.getChildAt(i)
            val isSelect = i == position
            val tab_title = tabView as TextView

            if (tab_title != null) {
                tab_title.setTextColor(if (isSelect) textSelectColor else textNormalColor)
                tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, if (isSelect) textSelectSize else textNormalSize)
                if (isTextBold) {
                    tab_title.paint.isFakeBoldText = isSelect
                }
            }
        }
    }

    override fun onPageSelected(position: Int) = updateTabSelection(position)

    override fun onPageScrollStateChanged(state: Int) = Unit

    internal inner class InnerPagerAdapter(fm: FragmentManager, fragments: ArrayList<Fragment>, private val titles: Array<String>) : FragmentPagerAdapter(fm) {
        private var fragments = ArrayList<Fragment>()

        init {
            this.fragments = fragments
        }

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence = titles[position]

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) =// 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
                // super.destroyItem(container, position, object);
                Unit

        override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE
    }

    interface OnTagSelectListener {
        fun onTagSelected(position: Int, tag: String)
    }

    fun addOnTagSelectListener(listener: OnTagSelectListener) {
        selectListener = listener
    }

    private fun dp2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun sp2px(context: Context, sp: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }
}
