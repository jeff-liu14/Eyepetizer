package com.moment.eyepetizer.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import com.moment.eyepetizer.R

/**
 * Created by moment on 2018/2/8.
 */

class SearchView(context: Context, attrs: AttributeSet) : android.support.v7.widget.AppCompatEditText(context, attrs), View.OnFocusChangeListener, TextWatcher {
    private var searchSize = 0f
    internal var textSize = 0f
    internal var textColor = -0x1000000
    var textHint: String? = null
    private var mDrawable: Drawable? = null
    private var paint: Paint? = null
    //右边的删除按钮
    private var mClearDrawable: Drawable? = null

    private var mContext: Context? = null

    private var buttonClick: ClearButtonClick? = null


    init {
        initResource(context, attrs)
        initPaint()
        init(context)
    }

    private fun init(context: Context) {
        this.mContext = context
        mClearDrawable = mContext!!.resources.getDrawable(R.drawable.icon_close)
        //设置删除按钮的边界
        mClearDrawable!!.setBounds(0, 0, mClearDrawable!!.intrinsicWidth * 2 / 5, mClearDrawable!!.intrinsicHeight * 2 / 5)
        //默认隐藏删除按钮
        setClearIcon(false)

        //监听EditText焦点变化，以根据text长度控制删除按钮的显示、隐藏
        onFocusChangeListener = this
        //监听文本内容变化
        addTextChangedListener(this)
    }

    /**
     * 控制EditText右边制删除按钮的显示、隐藏
     */
    private fun setClearIcon(isShow: Boolean) {
        val rightDrawable = if (isShow) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1],
                rightDrawable, compoundDrawables[3])
    }


    private fun initResource(context: Context, attrs: AttributeSet) {
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchEdit)
        val density = context.resources.displayMetrics.density
        searchSize = mTypedArray.getDimension(R.styleable.SearchEdit_imageWidth, 18 * density + 0.5f)
        textColor = mTypedArray.getColor(R.styleable.SearchEdit_textColor, -0x7b7b7c)
        textSize = mTypedArray.getDimension(R.styleable.SearchEdit_textSize, 14 * density + 0.5f)
        textHint = mTypedArray.getString(R.styleable.SearchEdit_textHint)
        if (textHint == null) {
            textHint = "快来搜索吧~"
        }
        mTypedArray.recycle()
    }

    private fun initPaint() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = textColor
        paint!!.textSize = textSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawSearchIcon(canvas)
    }

    private fun drawSearchIcon(canvas: Canvas) {
        if (this.text.toString().isEmpty()) {
            val textWidth = paint!!.measureText(textHint)

            val dx = (width.toFloat() - 2.5 * searchSize - textWidth) / 2
            val dy = (height - searchSize) / 2

            canvas.save()
            canvas.translate(scrollX + dx.toFloat(), scrollY + dy)
            if (mDrawable != null) {
                mDrawable!!.draw(canvas)
            }
            canvas.restore()
            hint = textHint!!
            requestFocus()
            setSelection(0)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mDrawable == null) {
            try {
                mDrawable = context.resources.getDrawable(R.drawable.ic_action_search_no_padding)
                mDrawable!!.setBounds(0, 0, searchSize.toInt(), searchSize.toInt())
            } catch (e: Exception) {

            }

        }
    }

    override fun onDetachedFromWindow() {
        if (mDrawable != null) {
            mDrawable!!.callback = null
            mDrawable = null
        }
        super.onDetachedFromWindow()
    }

    private fun getFontLeading(paint: Paint): Float {
        val fm = paint.fontMetrics
        return fm.bottom - fm.top
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

    /**
     * 文本内容变化时回调
     * 当文本长度大于0时显示删除按钮， 否则隐藏
     *
     * @param text
     * @param start
     * @param lengthBefore
     * @param lengthAfter
     */
    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        setClearIcon(text.isNotEmpty())
    }

    override fun afterTextChanged(s: Editable) = Unit

    override fun onFocusChange(v: View, hasFocus: Boolean) = if (hasFocus) {
        setClearIcon(text.isNotEmpty())
    } else {
        setClearIcon(false)
    }

    /**
     * 通过手指的触摸位置模式删除按钮的点击事件
     *
     * @param event
     * @return
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                val xTouchable = event.x > width - paddingRight - mClearDrawable!!.intrinsicWidth && event.x < width - paddingRight

                val yTouchable = event.y > (height - mClearDrawable!!.intrinsicHeight) / 2 && event.y < (height + mClearDrawable!!.intrinsicHeight) / 2

                //清除文本
                if (xTouchable && yTouchable) {
                    setText("")
                    if (buttonClick != null) {
                        buttonClick!!.clear()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    interface ClearButtonClick {
        fun clear()
    }

    fun addOnClearClickListener(buttonClick: ClearButtonClick) {
        this.buttonClick = buttonClick
    }
}
