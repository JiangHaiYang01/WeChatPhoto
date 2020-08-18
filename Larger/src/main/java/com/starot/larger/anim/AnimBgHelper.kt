package com.starot.larger.anim

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.View
import com.starot.larger.utils.ColorTool

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
                ColorTool.getColorWithAlpha(Color.BLACK, (animation.animatedValue as Float))
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
                ColorTool.getColorWithAlpha(Color.BLACK, (animation.animatedValue as Float))
            )
        }
        valueAnimator.start()
    }

}