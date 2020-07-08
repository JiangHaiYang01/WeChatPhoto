package com.starot.larger.anim

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.View
import com.starot.larger.tool.ColorTool

object AnimBgExitHelper {
    //修改退出的时候背景 渐变 黑色
    fun start(
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