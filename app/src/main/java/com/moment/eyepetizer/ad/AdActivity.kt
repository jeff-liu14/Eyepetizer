package com.moment.eyepetizer.ad

import android.content.Intent
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import com.moment.eyepetizer.MainActivity
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseActivity
import kotlinx.android.synthetic.main.ad_activity.*

/**
 * Created by moment on 2018/2/2.
 */

class AdActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.ad_activity
    }

    override fun initView() {
        val font: Typeface = Typeface.createFromAsset(this.assets, "fonts/Lobster-1.4.otf")
        tv_english_intro!!.typeface = font
    }

    override fun initData() {
//        val alphaAnimation = AlphaAnimation(0.1f, 1.0f)
//        alphaAnimation.duration = 1000
        val scaleAnimation = ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.duration = 2000
        val animationSet = AnimationSet(true)
//        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(scaleAnimation)
        animationSet.duration = 2000
        bg!!.startAnimation(animationSet)
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                val intent: Intent = Intent(this@AdActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


    }

}