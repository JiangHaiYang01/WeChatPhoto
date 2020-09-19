package com.starot.larger.anim.impl

import android.content.Context
import android.graphics.Color
import android.view.View
import com.starot.larger.Larger
import com.starot.larger.anim.AnimBgHelper
import com.starot.larger.anim.AnimDragHelper
import com.starot.larger.anim.AnimExitHelper
import com.starot.larger.enums.AnimType
import com.starot.larger.impl.OnLargerConfigListener
import com.starot.larger.utils.ColorTool
import com.starot.larger.utils.LogUtils
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

interface OnDragAnimListener : OnLargerConfigListener {

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


        //如果将upCanMove 设置成true 就给他改变颜色
        val upCanMove = Larger.largerConfig?.upCanMove
        if (upCanMove == null || upCanMove == false) {
            //已经向上了 就黑色背景 不需要改动了
            if (y > 0) {
                //背景的颜色 变化
                val scale: Float = abs(y) / getWindowHeight(view.context)
                AnimDragHelper.currentScale = 1 - scale
                parent.setBackgroundColor(
                    ColorTool.getColorWithAlpha(getBackGroundColor(), 1 - scale)
                )
            }
        } else {
            val scale: Float = abs(y) / getWindowHeight(view.context)
            AnimDragHelper.currentScale = 1 - scale
            parent.setBackgroundColor(
                ColorTool.getColorWithAlpha(getBackGroundColor(), 1 - scale)
            )
        }

    }

    fun endDrag(view: View?) {
        if (view == null) {
            LogUtils.i("endDrag view is null")
            return
        }
        if (abs(view.translationY) < view.height * 0.4f) {
            onDragResume(AnimDragHelper.currentScale, view)
        } else {
            onDragExit(AnimDragHelper.currentScale, view)

        }
    }

    fun onDragExit(scale: Float, fullView: View)

    fun onDragResume(scale: Float, fullView: View)


    //手机屏幕 高度
    fun getWindowHeight(context: Context): Int {
        return context.applicationContext.resources.displayMetrics.heightPixels
    }
}