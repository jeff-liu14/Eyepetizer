package com.moment.eyepetizer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moment.eyepetizer.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by moment on 2018/2/22.
 * FlycoTabLayout 仿写，并提取部分功能原地址链接：
 * https://github.com/H07000223/FlycoTabLayout
 */

public class TabLayout extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    private Context mContext;
    private float textNormalSize;
    private float textSelectSize;
    private int textNormalColor;
    private int textSelectColor;
    private float underlineHeight;
    private int underlineColor;
    private float indicatorHeight;
    private float indicatorWidth;
    private float indicatorSpacing;
    private boolean isTextBold;
    private float tabPadding;
    private int indicatorColor;
    private boolean tabSpaceEqual;


    private OnTagSelectListener selectListener;
    private LinearLayout mTabsContainer;
    private int mCurrentTab = 0;
    private ViewPager mViewPager;
    private int mTabCount;
    private ArrayList<String> mTitles;

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initResource(context, attrs);
    }

    private Paint mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    private void initResource(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTabLayout);
        textNormalSize = typedArray.getDimension(R.styleable.MyTabLayout_textNormalSize, sp2px(context, 14));
        textSelectSize = typedArray.getDimension(R.styleable.MyTabLayout_textSelectSize, textNormalSize);
        textNormalColor = typedArray.getColor(R.styleable.MyTabLayout_textNormalColor, Color.GRAY);
        textSelectColor = typedArray.getColor(R.styleable.MyTabLayout_textSelectColor, Color.BLACK);
        underlineHeight = typedArray.getDimension(R.styleable.MyTabLayout_underLineHeight, dp2px(context, 0.5f));
        indicatorWidth = typedArray.getDimension(R.styleable.MyTabLayout_indicatorWidth, -1);
        indicatorHeight = typedArray.getDimension(R.styleable.MyTabLayout_indicatorHeight, dp2px(mContext, 3));
        indicatorSpacing = typedArray.getDimension(R.styleable.MyTabLayout_indicatorSpacing, dp2px(mContext, 6));
        isTextBold = typedArray.getBoolean(R.styleable.MyTabLayout_isTextBold, true);
        underlineColor = typedArray.getColor(R.styleable.MyTabLayout_underlineColor, Color.parseColor("#EEEEEE"));
        indicatorColor = typedArray.getColor(R.styleable.MyTabLayout_indicatorColor, Color.BLACK);
        tabSpaceEqual = typedArray.getBoolean(R.styleable.MyTabLayout_tabSpaceEqual, false);
        tabPadding = typedArray.getDimension(R.styleable.MyTabLayout_tabPadding, tabSpaceEqual ? dp2px(mContext, 5) : dp2px(mContext, 20));
        typedArray.recycle();

        mRectPaint.setColor(underlineColor);
        mIndicatorDrawable.setColor(indicatorColor);
        mTabsContainer = new LinearLayout(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mTabsContainer.setLayoutParams(lp);
        mTabsContainer.setGravity(Gravity.CENTER_VERTICAL);
        setFillViewport(true);
        setWillNotDraw(false);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        addView(mTabsContainer);

    }

    //setter and getter
    public void setCurrentTab(int currentTab) {
        this.mCurrentTab = currentTab;
        mViewPager.setCurrentItem(currentTab);

    }

    public void setCurrentTab(int currentTab, boolean smoothScroll) {
        this.mCurrentTab = currentTab;
        mViewPager.setCurrentItem(currentTab, smoothScroll);
    }

    public int getTabCount() {
        return mTabCount;
    }

    public void addTab(final int position, final String title, TextView textView) {
        if (textView != null) {
            if (title != null) {
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textNormalSize);
                textView.setTextColor(textNormalColor);
                textView.setText(title);
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = mTabsContainer.indexOfChild(v);
                        if (position != -1) {
                            if (mViewPager.getCurrentItem() != position) {
                                mViewPager.setCurrentItem(position);
                            } else {
                                if (selectListener != null) {
                                    selectListener.onTagSelected(position, mTitles != null ? mTitles.get(position) : mViewPager.getAdapter().getPageTitle(position).toString());
                                }
                            }
                        }
                    }
                });
            }
        }
        LinearLayout.LayoutParams lp = tabSpaceEqual ?
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f) :
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        mTabsContainer.addView(textView, position, lp);

    }

    private GradientDrawable mIndicatorDrawable = new GradientDrawable();
    /**
     * indicator
     */

    private float mIndicatorMarginLeft = 0;
    private float mIndicatorMarginTop = 0;
    private float mIndicatorMarginRight = 0;
    /**
     * 用于绘制显示器
     */
    private Rect mIndicatorRect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int top = 0, bottom = height;

        if (mTabsContainer != null && mTabsContainer.getChildAt(mCurrentTab) != null) {
            top = mTabsContainer.getChildAt(mCurrentTab).getTop();
            bottom = mTabsContainer.getChildAt(mCurrentTab).getBottom();
        }

        int tabHeight = top + bottom;

        canvas.drawRect(paddingLeft, height - underlineHeight, mTabsContainer.getWidth() + paddingLeft, height, mRectPaint);

        calcIndicatorRect();

        if (indicatorHeight > 0 && indicatorWidth > 0) {
            mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                    tabHeight - (int) tabPadding * 2 + (int) indicatorSpacing,
                    paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                    tabHeight - (int) tabPadding * 2 + (int) indicatorSpacing + (int) indicatorHeight);

            mIndicatorDrawable.draw(canvas);
        } else {
            mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                    height - (int) indicatorHeight,
                    paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                    height);
            mIndicatorDrawable.draw(canvas);
        }
    }

    private float mCurrentPositionOffset;

    private void calcIndicatorRect() {
        View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
        if (currentTabView == null) return;
        float left = currentTabView.getLeft();
        float right = currentTabView.getRight();

        if (this.mCurrentTab < mTabCount - 1) {
            View nextTabView = mTabsContainer.getChildAt(this.mCurrentTab + 1);
            float nextTabLeft = nextTabView.getLeft();
            float nextTabRight = nextTabView.getRight();

            left = left + mCurrentPositionOffset * (nextTabLeft - left);
            right = right + mCurrentPositionOffset * (nextTabRight - right);
        }

        mIndicatorRect.left = (int) left;
        mIndicatorRect.right = (int) right;

        mTabRect.left = (int) left;
        mTabRect.right = (int) right;

        if (indicatorWidth >= 0) {
            //indicatorWidth大于0时,圆角矩形以及三角形
            float indicatorLeft = currentTabView.getLeft() + (currentTabView.getWidth() - indicatorWidth) / 2;

            if (this.mCurrentTab < mTabCount - 1) {
                View nextTab = mTabsContainer.getChildAt(this.mCurrentTab + 1);
                indicatorLeft = indicatorLeft + mCurrentPositionOffset * (currentTabView.getWidth() / 2 + nextTab.getWidth() / 2);
            }

            mIndicatorRect.left = (int) indicatorLeft;
            mIndicatorRect.right = (int) (mIndicatorRect.left + indicatorWidth);
        }
    }

    /**
     * 关联ViewPager,用于不想在ViewPager适配器中设置titles数据的情况
     */
    public void setViewPager(ViewPager vp, String[] titles) {
        if (vp == null || vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager or ViewPager adapter can not be NULL !");
        }

        if (titles == null || titles.length == 0) {
            throw new IllegalStateException("Titles can not be EMPTY !");
        }

        if (titles.length != vp.getAdapter().getCount()) {
            throw new IllegalStateException("Titles length must be the same as the page count !");
        }

        this.mViewPager = vp;
        mTitles = new ArrayList<>();
        Collections.addAll(mTitles, titles);

        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    /**
     * 关联ViewPager,用于连适配器都不想自己实例化的情况
     */
    public void setViewPager(ViewPager vp, String[] titles, FragmentActivity fa, ArrayList<Fragment> fragments) {
        if (vp == null) {
            throw new IllegalStateException("ViewPager can not be NULL !");
        }

        if (titles == null || titles.length == 0) {
            throw new IllegalStateException("Titles can not be EMPTY !");
        }

        this.mViewPager = vp;
        this.mViewPager.setAdapter(new InnerPagerAdapter(fa.getSupportFragmentManager(), fragments, titles));

        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    /**
     * 更新数据
     */
    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        this.mTabCount = mTitles == null ? mViewPager.getAdapter().getCount() : mTitles.size();
        TextView tabView;
        for (int i = 0; i < mTabCount; i++) {
            tabView = new TextView(mContext);
            CharSequence pageTitle = mTitles == null ? mViewPager.getAdapter().getPageTitle(i) : mTitles.get(i);
            addTab(i, pageTitle.toString(), tabView);
        }

        updateTabStyles();
    }

    private void updateTabStyles() {
        for (int i = 0; i < mTabCount; i++) {
            View v = mTabsContainer.getChildAt(i);
            TextView tv_tab_title = (TextView) v;
            if (tv_tab_title != null) {
                tv_tab_title.setTextColor(i == mCurrentTab ? textSelectColor : textNormalColor);
                tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, i == mCurrentTab ? textSelectSize : textNormalSize);
                tv_tab_title.setPadding((int) tabPadding, (int) tabPadding, (int) tabPadding, (int) tabPadding);
                if (isTextBold) {
                    tv_tab_title.getPaint().setFakeBoldText(i == mCurrentTab);
                }

            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.mCurrentTab = position;
        this.mCurrentPositionOffset = positionOffset;
        scrollToCurrentTab();
        invalidate();
    }

    /**
     * 用于实现滚动居中
     */
    private Rect mTabRect = new Rect();
    private int mLastScrollX;

    /**
     * HorizontalScrollView滚到当前tab,并且居中显示
     */
    private void scrollToCurrentTab() {
        if (mTabCount <= 0) {
            return;
        }

        int offset = (int) (mCurrentPositionOffset * mTabsContainer.getChildAt(mCurrentTab).getWidth());
        /**当前Tab的left+当前Tab的Width乘以positionOffset*/
        int newScrollX = mTabsContainer.getChildAt(mCurrentTab).getLeft() + offset;

        if (mCurrentTab > 0 || offset > 0) {
            /**HorizontalScrollView移动到当前tab,并居中*/
            newScrollX -= getWidth() / 2 - getPaddingLeft();
            calcIndicatorRect();
            newScrollX += ((mTabRect.right - mTabRect.left) / 2);
        }

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX;
            /** scrollTo（int x,int y）:x,y代表的不是坐标点,而是偏移量
             *  x:表示离起始位置的x水平方向的偏移量
             *  y:表示离起始位置的y垂直方向的偏移量
             */
            scrollTo(newScrollX, 0);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mCurrentTab", mCurrentTab);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentTab = bundle.getInt("mCurrentTab");
            state = bundle.getParcelable("instanceState");
            if (mCurrentTab != 0 && mTabsContainer.getChildCount() > 0) {
                updateTabSelection(mCurrentTab);
                scrollToCurrentTab();
            }
        }
        super.onRestoreInstanceState(state);
    }

    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title = (TextView) tabView;

            if (tab_title != null) {
                tab_title.setTextColor(isSelect ? textSelectColor : textNormalColor);
                tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, isSelect ? textSelectSize : textNormalSize);
                if (isTextBold) {
                    tab_title.getPaint().setFakeBoldText(isSelect);
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        updateTabSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class InnerPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments = new ArrayList<>();
        private String[] titles;

        public InnerPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
            // super.destroyItem(container, position, object);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    public interface OnTagSelectListener {
        void onTagSelected(int position, String tag);
    }

    public void addOnTagSelectListener(OnTagSelectListener listener) {
        selectListener = listener;
    }

    protected int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
