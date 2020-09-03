package com.starot.larger.anim.impl

import android.content.Context
import android.graphics.Color
import android.view.View
import com.starot.larger.Larger
import com.starot.larger.utils.ColorTool
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

interface OnDragAnimListener {
    //开始移动
    fun startDrag(parent: View, view: View?, x: Float, y: Float) {
        if (view == null) {
            return
        }

        //图片的缩放 和位置变化
        val fixedOffsetY = y - 0
        val fraction = abs(max(-1f, min(1f, fixedOffsetY / view.height)))
        val fakeScale = 1 - min(0.4f, fraction)
        view.scaleX = fakeScale
        view.scaleY = fakeScale
        view.translationY = fixedOffsetY
        view.translationX = x


        //已经向上了 就黑色背景 不需要改动了
        if (y > 0) {
            //背景的颜色 变化
            val scale: Float = abs(y) / getWindowHeight(view.context)
            parent.setBackgroundColor(
                ColorTool.getColorWithAlpha(Color.BLACK, 1 - scale)
            )
        }
    }


    //手机屏幕 高度
    fun getWindowHeight(context: Context): Int {
        return context.applicationContext.resources.displayMetrics.heightPixels
    }
}