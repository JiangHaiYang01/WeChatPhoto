package com.starot.larger.impl

import android.content.Context
import android.graphics.Color
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.utils.ColorTool
import com.starot.larger.utils.Drag
import com.starot.larger.view.image.PhotoView
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

//图片移动
interface OnDragAnimListener {


    //开始移动
    fun startDrag(context: Context, image: PhotoView, x: Float, y: Float) {
        //是否可以方法缩小 移动过程中不可以方法缩小
        image.setCustomZoomable(false)
        onDragStart(image)

        //默认拖动时候的阻尼系数
        var dampingData = getDamp()
        if (dampingData >= 1.0f) {
            dampingData = 1.0f
        } else if (dampingData < 0f) {
            dampingData = 0f
        }

        //图片的缩放 和位置变化
        val fixedOffsetY = y - 0
        val fraction = abs(max(-1f, min(1f, fixedOffsetY / image.height)))
        val fakeScale = 1 - min(0.4f, fraction)
        image.scaleX = fakeScale
        image.scaleY = fakeScale
        image.translationY = fixedOffsetY * dampingData
        image.translationX = x * dampingData


        //已经向上了 就黑色背景 不需要改动了
        if (y > 0) {
            //背景的颜色 变化
            val scale: Float = abs(y) / getWindowHeight(context)
            Drag.currentScale = 1 - scale
            onDragBackgroundColor(
                ColorTool.getColorWithAlpha(Color.BLACK, 1 - scale)
            )
        }
    }

    //停止移动
    fun stopDrag(
        image: PhotoView,
        holder: ViewPagerAdapter.PhotoViewHolder
    ) {
        image.setCustomZoomable(false)
        onDragStop(image)

        var fraction = getFraction()
        if (fraction > 1f) {
            fraction = 1f
        } else if (fraction < 0f) {
            fraction = 0f
        }
        if (abs(image.translationY) < image.height * fraction) {
            onDragResume(Drag.currentScale, image, holder)
        } else {
            onDragExit(Drag.currentScale, image, holder)
        }

    }

    //默认拖动时候的阻尼系数
    open fun getDamp(): Float {
        return 1.0f
    }

    //设置下拉的参数 [0.0f----1.0f] 越小越容易退出
    open fun getFraction(): Float {
        return 0.5f
    }


    //手机屏幕 高度
    fun getWindowHeight(context: Context): Int {
        return context.applicationContext.resources.displayMetrics.heightPixels
    }

    //背景颜色
    fun onDragBackgroundColor(color: Int)

    //开始移动
    fun onDragStart(image: PhotoView)

    //停止移动
    fun onDragStop(image: PhotoView)

    //退出动画
    fun onDragExit(currentScale: Float, image: PhotoView, holder: ViewPagerAdapter.PhotoViewHolder)

    //还原动画
    fun onDragResume(
        currentScale: Float,
        image: PhotoView,
        holder: ViewPagerAdapter.PhotoViewHolder
    )
}