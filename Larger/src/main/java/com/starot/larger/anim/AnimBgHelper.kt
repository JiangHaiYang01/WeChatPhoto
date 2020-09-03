package com.starot.larger.anim

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.View

object AnimBgHelper {
    //修改进入的时候背景 渐变 黑色
    fun enter(
        parent: View,
        originalScale: Float,
        duration: Long
    ) {
        val valueAnimator = ValueAnimator()
        valueAnimator.duration = duration
        valueAnimator.setFloatValues(originalScale, 1f)
        valueAnimator.addUpdateListener { animation ->
            parent.setBackgroundColor(
                getColorWithAlpha(Color.BLACK, (animation.animatedValue as Float))
            )
        }
        valueAnimator.start()
    }


    //修改退出的时候背景 渐变 黑色
    fun exit(
        parent: View,
        start: Float,
        duration: Long
    ) {
        startWithRange(start, 0f, parent, duration)
    }


    private fun startWithRange(
        start: Float,
        end: Float,
        parent: View,
        duration: Long
    ) {
        val valueAnimator = ValueAnimator()
        valueAnimator.duration = duration
        valueAnimator.setFloatValues(start, end)
        valueAnimator.addUpdateListener { animation ->
            parent.setBackgroundColor(
                getColorWithAlpha(Color.BLACK, (animation.animatedValue as Float))
            )
        }
        valueAnimator.start()
    }

    /**
     * 对rgb色彩加入透明度
     * @param alpha     透明度，取值范围 0.0f -- 1.0f.
     * @param baseColor
     * @return a color with alpha made from base color
     */
    private fun getColorWithAlpha(baseColor: Int, alpha: Float): Int {
        val a = 255.coerceAtMost(0.coerceAtLeast((alpha * 255).toInt())) shl 24
        val rgb = 0x00ffffff and baseColor
        return a + rgb
    }

}