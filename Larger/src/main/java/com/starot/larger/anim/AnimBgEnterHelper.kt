package com.starot.larger.anim

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.View
import com.starot.larger.tool.ColorTool

object AnimBgEnterHelper {
    //修改进入的时候背景 渐变 黑色
    fun start(
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
}